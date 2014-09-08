package com.genefo.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.RegionDAO;
import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;
import com.genefo.services.RegionService;

/**
 * 
 * @author Alexey
 *
 */
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
@Service("regionService")
public class RegionServiceImpl extends ReadOnlyServiceImpl<Region, Integer> implements RegionService {

	private static Logger logger = Logger.getLogger(RoleServiceImpl.class);
	
	@Autowired
	RegionDAO regionDao;
	
	@Override
	public GenericDao<Region, Integer> getDAO() {
		return regionDao;
	}

	@Override
	public Logger getServiceLogger() {
		return logger;
	}

	public List<Region> getRegionsByCountry(Country country) {
		return regionDao.getRegionsByCountry(country);
	}

}
