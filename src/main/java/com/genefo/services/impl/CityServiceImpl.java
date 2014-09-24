package com.genefo.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.CityDAO;
import com.genefo.persistence.models.City;
import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;
import com.genefo.services.CityService;

/**
 * 
 * @author Alexey
 *
 */
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
@Service("cityService")
public class CityServiceImpl extends ReadOnlyServiceImpl<City, Integer> implements CityService<Integer> {

	private static Logger logger = Logger.getLogger(CityServiceImpl.class);
	
	@Autowired
	CityDAO cityDao;
	
	@Override
	public GenericDao<City, Integer> getDAO() {
		return cityDao;
	}

	@Override
	public Logger getServiceLogger() {
		return logger;
	}


	public List<City> getCitiesByCountryAndRegion(Country country, Region region) {
		getServiceLogger().debug("In function getCitiesByCountryAndRegion(" + country + ", " + region +") ");
		return cityDao.getCitiesByCountryAndRegion(country, region);		
	}

	public List<City> getCitiesByCountry(Country country) {
		getServiceLogger().debug("In function getCitiesByCountry(" + country + ") ");
		return cityDao.getCitiesByCountry(country);		
	}

}
