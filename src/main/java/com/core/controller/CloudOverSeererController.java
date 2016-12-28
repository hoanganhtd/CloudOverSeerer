/**
 * HoangAnh
 * Dec 18, 2016
 * 
 */
package com.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.configs.AppConstants;
import com.core.model.APIInfo;
import com.core.model.LookUp;
import com.core.model.Schedule;
import com.core.model.ScheduleStatus;
import com.core.request.APIBody;
import com.core.request.RegisterRequest;
import com.core.request.ScheduleRequest;
import com.core.response.AppResponse;
import com.core.schedule.CloudOverSeererSchedule;
import com.core.service.CloudOverSeererService;
import com.core.utils.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author HoangAnh
 *
 */

@RestController
public class CloudOverSeererController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CloudOverSeererService cloudOverSeererService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse registerAPI(HttpServletRequest request , @RequestBody RegisterRequest registerRequest) {
		String location = "registerAPI() ";
		logger.info(location + "START");
		AppResponse appResponse = new AppResponse();
		APIInfo apiInfo 		= new APIInfo();
		
		try {
			//check duplicate in database
			APIInfo apiInfoInDB 			= cloudOverSeererService.getAPIInfo(registerRequest.getApiToCall(), registerRequest.getMethod());
			List<String> instancesRequest 	= registerRequest.getApiBody().getInstanceIds();
			boolean hasInstancesInDB = false;
			for (String ins : instancesRequest) {
				if (cloudOverSeererService.getLookUp(AppConstants.LOOKUP_API_BY_INSTANCE, ins) != null) {
					hasInstancesInDB = true;
				}
			}
			if (apiInfoInDB != null && hasInstancesInDB) {
				appResponse.setMessage("API and Instance are ready in the database");
				appResponse.setStatus(AppResponse.FAILURE);
				apiInfoInDB.setSchedules(null);
				appResponse.setResponse(apiInfoInDB);
				return appResponse;
			}
			//end check duplicate in database
			
			//save data into database
			ObjectMapper mapper = new ObjectMapper();
			apiInfo.setMethod(registerRequest.getMethod().toUpperCase());
			apiInfo.setUrl(registerRequest.getApiToCall());
			apiInfo.setParameter(mapper.writeValueAsString(registerRequest.getApiBody()));
			apiInfo.setCreatedDate(new Date());
			apiInfo.setStatus(1);
			apiInfo.setUuid(UUID.randomUUID().toString());
			apiInfo.setCreatedBy(getIPAddress(request));
			
			cloudOverSeererService.saveOrUpdateAPIInfo(apiInfo);
			
			//save to lookup
			List<String> instances = registerRequest.getApiBody().getInstanceIds();
			List<LookUp> lookUps = new ArrayList<LookUp>();
			for (String instance : instances) {
				LookUp lookUp = new LookUp();
				lookUp.setName(AppConstants.LOOKUP_API_BY_INSTANCE);
				lookUp.setValue(instance);
				lookUp.setObject(APIInfo.class.getName());
				lookUp.setObjectUUID(apiInfo.getUuid());
				lookUp.setObjectId(apiInfo.getId());
				lookUps.add(lookUp);
			}
			if (!lookUps.isEmpty()) {
				cloudOverSeererService.saveOrUpdateLookUp(lookUps);
			}
		} catch (Exception e) {
			appResponse.setMessage(e.getMessage());
			appResponse.setStatus(AppResponse.FAILURE);
			return appResponse;
		}
		
		appResponse.setStatus(AppResponse.SUCCESS);
		appResponse.setResponse(apiInfo);
		logger.info(location + "END");
		return appResponse;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ResponseBody
	public RegisterRequest registerExample() {
		RegisterRequest registerRequest = new RegisterRequest();
		APIBody apiBody = new APIBody();
		ObjectMapper mapper = new ObjectMapper();
		apiBody.setStatus("terminate");
		apiBody.getInstanceIds().add("i-471ae551");
		apiBody.getInstanceIds().add("i-471ae552");
		registerRequest.setApiToCall("https://t5rtz6drn5.execute-api.us-east-1.amazonaws.com/dev/stop-terminate-instances");
		registerRequest.setApiBody(apiBody);
		registerRequest.setMethod("POST");
		return registerRequest;
	}
	
	@RequestMapping(value = "/lookup", method = RequestMethod.GET)
	@ResponseBody
	public AppResponse lookupAPI(@RequestParam String lookUpName, @RequestParam String lookUpVaule) {
		String location = "lookupAPI() ";
		logger.info(location + "START");
		logger.info(location + "lookUpName = " + lookUpName);
		logger.info(location + "lookUpVaule = " + lookUpVaule);
		AppResponse appResponse = new AppResponse();
		try {
			Object lookUp = null;
			
			if (!(AppConstants.LOOKUP_API_BY_UUID.equalsIgnoreCase(lookUpName.trim()) || AppConstants.LOOKUP_API_BY_INSTANCE.equalsIgnoreCase(lookUpName.trim()))) {
				appResponse.setMessage("INVALID LOOK_UP_NAME");
				appResponse.setStatus(AppResponse.FAILURE);
				return appResponse;
			}
			
			if (AppConstants.LOOKUP_API_BY_UUID.equalsIgnoreCase(lookUpName)) {
				lookUp = cloudOverSeererService.getAPIInfoByUUID(lookUpVaule);
			}
			
			if (AppConstants.LOOKUP_API_BY_INSTANCE.equalsIgnoreCase(lookUpName)) {
				LookUp lookUpResult = cloudOverSeererService.getLookUp(AppConstants.LOOKUP_API_BY_INSTANCE, lookUpVaule);
				if (lookUpResult != null) {
					lookUp = cloudOverSeererService.getAPIInfoByUUID(lookUpResult.getObjectUUID());
				}
			}
			
			if (lookUp == null) {
				appResponse.setMessage("NOT FOUND");
				appResponse.setStatus(AppResponse.FAILURE);
			} else {
				appResponse.setStatus(AppResponse.SUCCESS);
				appResponse.setResponse(lookUp);
			}
			
		} catch (Exception e) {
			appResponse.setMessage(e.getMessage());
			appResponse.setStatus(AppResponse.FAILURE);
			return appResponse;
		}
		
		logger.info(location + "END");
		return appResponse;
	}
	
	
	@RequestMapping(value = "/overseer", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse overseerPostSchedule(HttpServletRequest request, @RequestBody ScheduleRequest scheduleRequest) {
		String location = "overseerPostSchedule() ";
		logger.info(location + "START");
		AppResponse appResponse = new AppResponse();
		try {
			APIInfo apiInfo = cloudOverSeererService.getAPIInfoByUUID(scheduleRequest.getApiUUID());
			if (apiInfo == null) {
				appResponse.setMessage("NOT FOUND");
				appResponse.setStatus(AppResponse.FAILURE);
			} else {
				List<Schedule> schedules = apiInfo.getSchedules();
				boolean readySchedule = false;
				Schedule scheduleReady = new Schedule();
				for (Schedule schel : schedules) {
					if (schel.getStatus().equalsIgnoreCase(ScheduleStatus.PENDING.name())
							|| schel.getStatus().equalsIgnoreCase(ScheduleStatus.RUNNING.name()) ) {
						readySchedule = true;
						scheduleReady = schel;
					}
				}
				
				if (readySchedule) {
					appResponse.setMessage("SCHEDULE IS READY");
					appResponse.setStatus(AppResponse.FAILURE);
					apiInfo.getSchedules().clear();
					apiInfo.getSchedules().add(scheduleReady);
					appResponse.setResponse(apiInfo);
					return appResponse;
				}
				
				Schedule schedule = new Schedule();
				
				schedule.setFrequency(scheduleRequest.getFrequency());
				schedule.setStartJobAt(DataUtils.parseDate(scheduleRequest.getStartJobAt()));
				schedule.setEndJobAt(DataUtils.parseDate(scheduleRequest.getEndJobAt()));
				schedule.setIntervalTime(scheduleRequest.getIntervalTime());
				schedule.setRepeatCount(scheduleRequest.getRepeatCount());
				
				schedule.setStatus(ScheduleStatus.PENDING.name());
				schedule.setCreatedBy(getIPAddress(request));
				schedule.setCreatedDate(new Date());
				schedule.setApiInfo(apiInfo);
				schedule.setUuid(UUID.randomUUID().toString());
				
				cloudOverSeererService.saveOrUpdateSchedule(schedule);
				
				CloudOverSeererSchedule.getInstance().addAndApply(schedule);
				
				appResponse.setStatus(AppResponse.SUCCESS);
				
				apiInfo.getSchedules().clear();
				apiInfo.getSchedules().add(cloudOverSeererService.getScheduleById(schedule.getId()));
				appResponse.setResponse(apiInfo);
			}
		} catch (Exception e) {
			appResponse.setMessage(e.getMessage());
			appResponse.setStatus(AppResponse.FAILURE);
			return appResponse;
		}
		
		logger.info(location + "END");
		return appResponse;
	}
	
	@RequestMapping(value = "/stopSchedule", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse stopSchedule(HttpServletRequest request, @RequestParam String scheduleUUID) {
		String location = "overseerPostSchedule() ";
		logger.info(location + "START");
		AppResponse appResponse = new AppResponse();
		try {
			Schedule schedule = cloudOverSeererService.getScheduleByUUID(scheduleUUID);
			if (schedule != null) {
				CloudOverSeererSchedule.getInstance().deleteJob(scheduleUUID);
				APIInfo apiInfo = schedule.getApiInfo();
				apiInfo.getSchedules().clear();
				apiInfo.getSchedules().add(cloudOverSeererService.getScheduleByUUID(scheduleUUID));
				appResponse.setStatus(AppResponse.SUCCESS);
				appResponse.setResponse(apiInfo);
			} else {
				appResponse.setMessage("NOT FOUND");
				appResponse.setStatus(AppResponse.FAILURE);
			}
			
		} catch (Exception e) {
			appResponse.setMessage(e.getMessage());
			appResponse.setStatus(AppResponse.FAILURE);
		}
		
		logger.info(location + "END");
		return appResponse;
	}

	@RequestMapping(value = "/testLookUpAPI", method = RequestMethod.GET)
	@ResponseBody
	public AppResponse test(@RequestParam String uuid) {
		String location = "lookupAPI() ";
		AppResponse appResponse = new AppResponse();
		Schedule schedule = new Schedule();
		schedule.setUuid(UUID.randomUUID().toString());
		schedule.setFrequency("ONCE");
		schedule.setCreatedDate(new Date());
		//schedule.setStartJobAT(2L);
		CloudOverSeererSchedule.getInstance().addAndApply(schedule);
		logger.info(location + "END");
		return appResponse;
	}
	
	@RequestMapping(value = "/testLookUpSchedule", method = RequestMethod.GET)
	@ResponseBody
	public ScheduleRequest testSchedule() {
		String location = "lookupAPI() ";
		ScheduleRequest scheduleRequest = new ScheduleRequest();
		scheduleRequest.setApiUUID("test--");
		scheduleRequest.setStartJobAt(DataUtils.formatDate(new Date()));
		scheduleRequest.setEndJobAt(DataUtils.formatDate(new Date()));
		scheduleRequest.setIntervalTime(10);
		scheduleRequest.setRepeatCount(10);
		scheduleRequest.setFrequency("REPEAT");
		logger.info(location + "END");
		return scheduleRequest;
	}

	public String getIPAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
	
	private AppResponse validateSchedule(Schedule schedule) {
		AppResponse appResponse = new AppResponse();
		appResponse.setStatus(AppResponse.SUCCESS);
		return appResponse;
	}
}
