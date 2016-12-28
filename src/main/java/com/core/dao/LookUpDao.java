/**
 * HoangAnh
 * Dec 23, 2016
 * 
 */
package com.core.dao;


import org.springframework.data.repository.CrudRepository;

import com.core.model.LookUp;

/**
 * @author HoangAnh
 *
 */
public interface LookUpDao extends CrudRepository<LookUp, Long>, LookUpDaoCus{

}
