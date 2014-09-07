package com.genefo.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.CityDAO;
import com.genefo.persistence.dao.GenderDAO;
import com.genefo.persistence.dao.ProfileDAO;
import com.genefo.persistence.dao.RelationDAO;
import com.genefo.persistence.models.City;
import com.genefo.persistence.models.Gender;
import com.genefo.persistence.models.Profile;
import com.genefo.persistence.models.Relation;
import com.genefo.persistence.models.User;
import com.genefo.services.ProfileService;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
@Service("profileService")
public class ProfileServiceImpl extends BaseServiceImpl<Profile, Long>
		implements ProfileService {

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	ProfileDAO profileDao;
	
	@Autowired
	GenderDAO genderDao;
	
	@Autowired
	private CityDAO cityDao;
	
	@Autowired
	private RelationDAO relationDao;
	
	@Override
	public GenericDao<Profile, Long> getDAO() {
		return profileDao;
	}

	@Override
	public Logger getServiceLogger() {
		return logger;
	}
	
	public Profile createTestProfile() {
		Profile prof = new Profile();
		prof.setLastName("LastName");
		prof.setFirstName("FirstName");
		List<Gender> genders = genderDao.findAll();
		prof.setGender(genders.get(0));
		City cityexample = new City();
		cityexample.setId(1);
		cityexample = cityDao.findUniqueResultByExample(cityexample);
		prof.setCity(cityexample);
		Relation exampleRelation = new Relation();
		exampleRelation.setName("yourself");
		exampleRelation = relationDao.findUniqueResultByExample(exampleRelation);
		prof.setRelation(exampleRelation);	
		return prof;
	}
	
	

}
