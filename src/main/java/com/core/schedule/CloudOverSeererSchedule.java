/**
 * HoangAnh
 * Dec 17, 2016
 * 
 */
package com.core.schedule;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.util.Date;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.configs.AppConstants;
import com.core.model.Schedule;
import com.core.model.ScheduleStatus;
import com.core.service.CloudOverSeererService;

/**
 * @author HoangAnh
 *
 */
public class CloudOverSeererSchedule {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static CloudOverSeererSchedule instance;
	private Scheduler scheduler;
	private CloudOverSeererService cloudOverSeererService;

	private CloudOverSeererSchedule() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static CloudOverSeererSchedule getInstance() {
		if (instance == null) {
			instance = new CloudOverSeererSchedule();
		}
		return instance;
	}

	public void loadAndApplyStop() {
		String location = "loadAndApply() ";
		logger.info(location + "START");
		try {
			List<Schedule> schedules = cloudOverSeererService.getAllSchedule();
			for (Schedule schedule : schedules) {
				schedule.setStatus(ScheduleStatus.STOPED.name());
				cloudOverSeererService.saveOrUpdateSchedule(schedule);
			}
		} catch (Exception e) {
			logger.info(location + " >> " + e.getMessage(), e);
		}
	}

	public void addAndApply(Schedule schedule) {
		String location = "addAndApply() ";
		JobDetail job = createJob(schedule);
		Trigger trigger = createTrigger(schedule);
		try {
			scheduler.scheduleJob(job, trigger);
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();

	    	//Listener attached to jobKey
	    	scheduler.getListenerManager().addJobListener(
	    		new CloudOverSeerJobListener(), KeyMatcher.keyEquals(job.getKey())
	    	);
	    	
	    	if (isStop(schedule)) {
				schedule = cloudOverSeererService.getScheduleById(schedule.getId());
				schedule.setStatus(ScheduleStatus.FINISHED.name());
				cloudOverSeererService.saveOrUpdateSchedule(schedule);
				logger.info(location + "UPDATE SCHDULE >> FINISHED");
			}
	    	
		} catch (Exception e) {
			logger.info("addAndApply() " + e.getMessage(), e);
		}
	}

	public JobDetail createJob(Schedule schedule) {
		logger.info(">>> Create job with schedule scan id =  " + schedule.getId());
		logger.info(">>> Create job with schedule scan key =  " + schedule.getUuid());
		JobDetail job = JobBuilder.newJob(CloudOverSeererJob.class).withIdentity(schedule.getUuid() + "").build();
		logger.info(">>> Job key: " + job.getKey());
		job.getJobDataMap().put("SCHEDULE", schedule);
		job.getJobDataMap().put("CLOUDOVERSEERERSERVICE", cloudOverSeererService);
		return job;
	}

	public Trigger createTrigger(Schedule schedule) {
		logger.info("schedule.getFrequency() = " + schedule.getFrequency());
		if (AppConstants.SCHEDULE_ONCE.equalsIgnoreCase(schedule.getFrequency())) {
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(schedule.getUuid())
					.startAt(schedule.getStartJobAt()).build();
			return trigger;
		} else {
			//repeat count
			if (schedule.getRepeatCount() != null && schedule.getRepeatCount() > 0) {
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(schedule.getUuid())
						.withSchedule(simpleSchedule().withIntervalInMinutes(schedule.getIntervalTime())
								.withRepeatCount(schedule.getRepeatCount()))
						.startAt(schedule.getStartJobAt()).endAt(schedule.getEndJobAt()).build();
				return trigger;
			} else {
				//repeat forever
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(schedule.getUuid())
						.withSchedule(SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInMinutes(schedule.getIntervalTime()).repeatForever())
						.startAt(schedule.getStartJobAt()).endAt(schedule.getEndJobAt()).build();
				return trigger;
			}
		}
	}
	
	private boolean isStop(Schedule schedule) {
		logger.info("schedule.getFrequency() = " + schedule.getFrequency());
		if (AppConstants.SCHEDULE_ONCE.equalsIgnoreCase(schedule.getFrequency())) {
			if (schedule.getStartJobAt().compareTo(new Date()) < 0)
			return true;
		} else {
			//repeat count
			if (schedule.getEndJobAt() == null) return false;
			if (schedule.getRepeatCount() != null && schedule.getRepeatCount() > 0) {
				if (schedule.getEndJobAt().compareTo(new Date()) < 0) {
					return true;
				}
			} else {
				//repeat forever
				if (schedule.getEndJobAt().compareTo(new Date()) < 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void deleteJob(JobDetail job) {
		try {
			logger.info("deleteJob() START");
			logger.info("deleteJob() job key = " + job.getKey());
			scheduler.deleteJob(job.getKey());
			
			logger.info("deleteJob() END");
		} catch (SchedulerException e) {
			logger.error("deleteJob() " + e.getMessage(), e);
		}
	}
	
	public void deleteJob(String scheduleUUID) {
		try {
			logger.info("deleteJob() START");
			logger.info("deleteJob() job key = " + scheduleUUID);
			scheduler.deleteJob(new org.quartz.JobKey(scheduleUUID));
			
			Schedule schedule = cloudOverSeererService.getScheduleByUUID(scheduleUUID);
			schedule.setStatus(ScheduleStatus.STOPED.name());
			
			cloudOverSeererService.saveOrUpdateSchedule(schedule);
			
			logger.info("deleteJob() END");
		} catch (Exception e) {
			logger.error("deleteJob() " + e.getMessage(), e);
		}
	}
	
	

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public CloudOverSeererService getCloudOverSeererService() {
		return cloudOverSeererService;
	}

	public void setCloudOverSeererService(CloudOverSeererService cloudOverSeererService) {
		this.cloudOverSeererService = cloudOverSeererService;
	}

}
