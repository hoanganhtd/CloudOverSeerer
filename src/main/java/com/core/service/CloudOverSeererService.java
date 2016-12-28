/**
 * HoangAnh
 * Dec 18, 2016
 * 
 */
package com.core.service;

import java.util.List;

import com.core.model.APIInfo;
import com.core.model.LookUp;
import com.core.model.Schedule;

/**
 * @author HoangAnh
 *
 */
public interface CloudOverSeererService {
	
	public APIInfo saveOrUpdateAPIInfo(APIInfo apiInfo) throws Exception;
	
	public List<APIInfo> saveOrUpdateAPIInfo(List<APIInfo> apiInfos) throws Exception;
	
	public Schedule saveOrUpdateSchedule(Schedule schedule) throws Exception;
	
	public List<Schedule> saveOrUpdateSchedule(List<Schedule> schedules) throws Exception;

	public List<Schedule> getAllSchedule() throws Exception;
	
	public APIInfo getAPIInfo(String url, String method) throws Exception;
	
	public APIInfo getAPIInfoByUUID(String uuid) throws Exception;
	
	public LookUp getLookUp(String name, String value) throws Exception;
	
	public LookUp saveOrUpdateLookUp(LookUp lookUp) throws Exception;
	
	public List<LookUp> saveOrUpdateLookUp(List<LookUp> lookUps) throws Exception;
	
	public Schedule getScheduleById(Long id) throws Exception;
	
	public Schedule getScheduleByUUID(String uuid) throws Exception;
}
