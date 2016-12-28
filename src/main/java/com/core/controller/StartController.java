/**
 * HoangAnh
 * Dec 17, 2016
 * 
 */
package com.core.controller;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.core.schedule.CloudOverSeererSchedule;
import com.core.service.CloudOverSeererService;

/**
 * @author HoangAnh
 *
 */
@Component
public class StartController implements ApplicationListener<ContextRefreshedEvent> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CloudOverSeererService cloudOverSeererService;
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		String location = "onApplicationEvent() ";
		logger.info(location + "START");
		CloudOverSeererSchedule.getInstance().setCloudOverSeererService(cloudOverSeererService);
		try {
			CloudOverSeererSchedule.getInstance().loadAndApplyStop();
			CloudOverSeererSchedule.getInstance().getScheduler().start();
			logger.debug(">>> Start all schedules in database >>>");
			
		} catch (SchedulerException e) {
			logger.info(location + " >>> " + e.getMessage(), e);
		}
		logger.info(location + "END");
	}

}
