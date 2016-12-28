/**
 * HoangAnh
 * Dec 19, 2016
 * 
 */
package com.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 * @author HoangAnh
 *
 */
@Repository
@Transactional
public class ScheduleDaoImpl implements ScheduleDaoCus {
	
	@PersistenceContext
	private EntityManager entityManager;
}
