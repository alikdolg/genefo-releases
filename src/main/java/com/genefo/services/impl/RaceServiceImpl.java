package com.genefo.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.RaceDAO;
import com.genefo.persistence.models.Race;
import com.genefo.services.RaceService;

/**
 * 
 * @author Alexey
 *
 */
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
@Service("raceService")
public class RaceServiceImpl extends ReadOnlyServiceImpl<Race, Integer> implements RaceService {

	private static Logger logger = Logger.getLogger(RaceServiceImpl.class);
	
	@Autowired
	RaceDAO raceDao; 
	
	@Override
	public GenericDao<Race, Integer> getDAO() {
		return raceDao;
	}

	@Override
	public Logger getServiceLogger() {
		return logger;
	}

}
