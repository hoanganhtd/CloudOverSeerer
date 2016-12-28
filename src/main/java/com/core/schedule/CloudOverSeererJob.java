/**
 * HoangAnh
 * Dec 17, 2016
 * 
 */
package com.core.schedule;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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
public class CloudOverSeererJob implements Job {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String location = "execute() ";
		logger.info(location + "START");
		JobDetail job = context.getJobDetail();
		Schedule schedule = (Schedule) job.getJobDataMap().get("SCHEDULE");
		CloudOverSeererService cloudOverSeererService = (CloudOverSeererService) job.getJobDataMap().get("CLOUDOVERSEERERSERVICE");
		logger.info(location + "Schedule" + schedule);
		try {
			schedule = cloudOverSeererService.getScheduleById(schedule.getId());
			schedule.increaseExecutedCount();
			schedule.setStatus(ScheduleStatus.RUNNING.name());
			cloudOverSeererService.saveOrUpdateSchedule(schedule);
		} catch (Exception e1) {
			logger.info(location + "exception >> " + e1);
		}
		
		//RestTemplate restTemplate = new RestTemplate();
		if (AppConstants.POST_METHOD.equalsIgnoreCase(schedule.getApiInfo().getMethod())) {
			logger.info(location + "CALL >> " + schedule.getApiInfo());
			//ResponseEntity<String> response = restTemplate.postForEntity(schedule.getApiInfo().getUrl(), schedule.getApiInfo().getParameter(), String.class);
			//logger.info(location + "RESPONSE >> " + response);
		} else {
			logger.info(location + "THIS VERSION DOESNOT SUPPORT THIS >> " + schedule.getApiInfo());
		}
		logger.info(location + "END");
	}
}
