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

/**
 * 
 * @author Alexey
 *
 */
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
@Service("genderService")
public class GenderServiceImpl extends ReadOnlyServiceImpl<Gender, Integer> implements GenderService<Integer> {

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
	
}
