/**
 * HoangAnh
 * Dec 18, 2016
 * 
 */
package com.core.dao;

import org.springframework.data.repository.CrudRepository;

import com.core.model.APIInfo;

/**
 * @author HoangAnh
 *
 */
public interface APIInfoDao extends CrudRepository<APIInfo, Long>, APIInfoDaoCus {

}
