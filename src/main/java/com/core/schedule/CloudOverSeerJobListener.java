/**
 * HoangAnh
 * Dec 21, 2016
 * 
 */
package com.core.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.model.Schedule;
import com.core.model.ScheduleStatus;
import com.core.service.CloudOverSeererService;

/**
 * @author HoangAnh
 *
 */
public class CloudOverSeerJobListener implements JobListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final String LISTENER_NAME = "CloudOverSeerJobListener";
	
	/* (non-Javadoc)
	 * @see org.quartz.JobListener#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return LISTENER_NAME;
	}

	/* (non-Javadoc)
	 * @see org.quartz.JobListener#jobToBeExecuted(org.quartz.JobExecutionContext)
	 */
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
	
	}

	/* (non-Javadoc)
	 * @see org.quartz.JobListener#jobExecutionVetoed(org.quartz.JobExecutionContext)
	 */
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
	
	}

	/* (non-Javadoc)
	 * @see org.quartz.JobListener#jobWasExecuted(org.quartz.JobExecutionContext, org.quartz.JobExecutionException)
	 */
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String location = "jobWasExecuted() ";
		logger.info(location + "START");
		Schedule schedule = (Schedule) context.getJobDetail().getJobDataMap().get("SCHEDULE");
		CloudOverSeererService cloudOverSeererService = (CloudOverSeererService) context.getJobDetail().getJobDataMap().get("CLOUDOVERSEERERSERVICE");
		logger.info(location + "Schedule" + schedule);
		logger.info(location + "context.getTrigger().mayFireAgain() >> " + context.getTrigger().mayFireAgain());
		try {
			if (!context.getTrigger().mayFireAgain()) {
				schedule = cloudOverSeererService.getScheduleById(schedule.getId());
				schedule.setStatus(ScheduleStatus.FINISHED.name());
				cloudOverSeererService.saveOrUpdateSchedule(schedule);
				logger.info(location + "UPDATE SCHDULE >> FINISHED");
			}
		} catch (Exception e1) {
			logger.info(location + "exception >> " + e1);
		}
		logger.info(location + "END");
	}

}
