package com.genefo.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.GenderDAO;
import com.genefo.persistence.models.Gender;
import com.genefo.services.GenderService;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
@Service("genderService")
public class GenderServiceImpl extends BaseServiceImpl<Gender, Integer> implements GenderService {

	private static Logger logger = Logger.getLogger(GenderServiceImpl.class);
	
	@Autowired
	GenderDAO genderDao;
	
	@Override
	public GenericDao<Gender, Integer> getDAO() {
		return genderDao;
	}

	@Override
	public Logger getServiceLogger() {
		return logger;
	}

	@Override
	public Gender add(Gender entity) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Add does not supported for read-only object.");
	}

	@Override
	public void update(Gender entity) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Update does not supported for read-only object.");
	}

	@Override
	public void remove(Gender entity) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Remove does not supported for read-only object.");
	}

	@Override
	public void delete(Gender[] entities) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Delete does not supported for read-only object.");
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Delete does not supported for read-only object.");
	}

	
}
