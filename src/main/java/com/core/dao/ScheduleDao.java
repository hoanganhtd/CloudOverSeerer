/**
 * HoangAnh
 * Dec 19, 2016
 * 
 */
package com.core.dao;

import org.springframework.data.repository.CrudRepository;

import com.core.model.Schedule;

/**
 * @author HoangAnh
 *
 */
public interface ScheduleDao extends CrudRepository<Schedule, Long>, ScheduleDaoCus {

}
