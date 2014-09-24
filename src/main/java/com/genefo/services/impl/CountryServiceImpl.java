package com.genefo.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.CountryDAO;
import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;
import com.genefo.services.CountryService;

/**
 * 
 * @author Alexey
 *
 */
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
@Service("countryService")
public class CountryServiceImpl extends ReadOnlyServiceImpl<Country, Integer> implements CountryService<Integer> {

	private static Logger logger = Logger.getLogger(CountryServiceImpl.class);
	
	@Autowired
	CountryDAO countryDao;
	
//	public List<Country> getCountriesByRegion(Region region) {
//		getServiceLogger().debug("In function getCountriesByRegion(" + region + ") ");
//		return countryDao.getCountryByRegion(region);		
//	}

	@Override
	public GenericDao<Country, Integer> getDAO() {
		return countryDao;
	}

	@Override
	public Logger getServiceLogger() {
		return logger;
	}

	public List<Country> getCountryByName(String name) {
		getServiceLogger().debug("In function getCountryByName(" + name + ") ");
		return countryDao.getCountryByName(name);
	}

	public List<Country> getCountryByCode(String code) {
		getServiceLogger().debug("In function getCountryByCode(" + code + ") ");
		return countryDao.getCountryByCode(code);
	}

	
}
