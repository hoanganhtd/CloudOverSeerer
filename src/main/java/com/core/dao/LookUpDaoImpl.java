/**
 * HoangAnh
 * Dec 23, 2016
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

@Transactional
@Repository
public class LookUpDaoImpl implements LookUpDaoCus{
	@PersistenceContext
	private EntityManager entityManager;
}
