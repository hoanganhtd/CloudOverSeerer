/**
 * HoangAnh
 * Dec 18, 2016
 * 
 */
package com.core.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.APIInfoDao;
import com.core.dao.LookUpDao;
import com.core.dao.ScheduleDao;
import com.core.model.APIInfo;
import com.core.model.LookUp;
import com.core.model.Schedule;
import com.core.service.CloudOverSeererService;

@Service
public class CloudOverSeererServiceImpl implements CloudOverSeererService{
	
	@Autowired
	private APIInfoDao apiInfoDao;
	
	@Autowired
	private ScheduleDao scheduleDao;
	
	@Autowired
	private LookUpDao lookUpDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#saveOrUpdateAPIInfo(com.core.model.APIInfo)
	 */
	@Override
	public APIInfo saveOrUpdateAPIInfo(APIInfo apiInfo) throws Exception {
		return apiInfoDao.save(apiInfo);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#saveOrUpdateAPIInfo(java.util.List)
	 */
	@Override
	public List<APIInfo> saveOrUpdateAPIInfo(List<APIInfo> apiInfos) throws Exception {
		return (List<APIInfo>) apiInfoDao.save(apiInfos);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#saveOrUpdateSchedule(com.core.model.Schedule)
	 */
	@Override
	public Schedule saveOrUpdateSchedule(Schedule schedule) throws Exception {
		return scheduleDao.save(schedule);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#saveOrUpdateSchedule(java.util.List)
	 */
	@Override
	public List<Schedule> saveOrUpdateSchedule(List<Schedule> schedules) throws Exception {
		return (List<Schedule>) scheduleDao.save(schedules);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#getAllSchedule()
	 */
	@Override
	public List<Schedule> getAllSchedule() throws Exception {
		Query q = entityManager.createQuery("from Schedule where status != 'FINISHED' and status != 'STOPED'");
		@SuppressWarnings("unchecked")
		List<Schedule> schedules = q.getResultList();
		return schedules;
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#getAPIInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public APIInfo getAPIInfo(String url, String method) throws Exception {
		String queryStr = "select * from cos_api_info where url = :url and method = :method";
		Query query = entityManager.createNativeQuery(queryStr, APIInfo.class);
		query.setParameter("url", url);
		query.setParameter("method", method);
		@SuppressWarnings("unchecked")
		List<APIInfo> apiInfos = query.getResultList();
		if (apiInfos.isEmpty()) {
			return null;
		}
		return apiInfos.get(0);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#getAPIInfo(java.lang.String)
	 */
	@Override
	public APIInfo getAPIInfoByUUID(String uuid) throws Exception {
		String queryStr = "select * from cos_api_info where uuid = :uuid";
		Query query = entityManager.createNativeQuery(queryStr, APIInfo.class);
		query.setParameter("uuid", uuid);
		@SuppressWarnings("unchecked")
		List<APIInfo> apiInfos = query.getResultList();
		if (apiInfos.isEmpty()) {
			return null;
		}
		return apiInfos.get(0);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#getAPIInfoByInstance(java.lang.String)
	 */
	@Override
	public LookUp getLookUp(String name, String value) throws Exception {
		String queryStr = "select * from cos_look_up where name = :name and value= :value";
		Query query = entityManager.createNativeQuery(queryStr, LookUp.class);
		query.setParameter("name", name);
		query.setParameter("value", value);
		
		@SuppressWarnings("unchecked")
		List<LookUp> lookUps = query.getResultList();
		if (lookUps.isEmpty()) {
			return null;
		}
		return lookUps.get(0);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#saveOrUpdateLookUp(com.core.model.LookUp)
	 */
	@Override
	public LookUp saveOrUpdateLookUp(LookUp lookUp) throws Exception {
		return lookUpDao.save(lookUp);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#saveOrUpdateLookUp(java.util.List)
	 */
	@Override
	public List<LookUp> saveOrUpdateLookUp(List<LookUp> lookUps) throws Exception {
		return (List<LookUp>)lookUpDao.save(lookUps);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#getScheduleById(java.lang.Long)
	 */
	@Override
	public Schedule getScheduleById(Long id) throws Exception {
		return scheduleDao.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.core.service.CloudOverSeererService#getScheduleByUUID(java.lang.String)
	 */
	@Override
	public Schedule getScheduleByUUID(String uuid) throws Exception {
		String queryStr = "select * from cos_schedule where uuid = :uuid";
		Query query = entityManager.createNativeQuery(queryStr, Schedule.class);
		query.setParameter("uuid", uuid);
		
		@SuppressWarnings("unchecked")
		List<Schedule> schedules = query.getResultList();
		if (schedules.isEmpty()) {
			return null;
		}
		return schedules.get(0);
	}

	
	
}
