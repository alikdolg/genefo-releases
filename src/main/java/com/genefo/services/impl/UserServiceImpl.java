package com.genefo.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.UserDAO;
import com.genefo.persistence.models.Gender;
import com.genefo.persistence.models.Profile;
import com.genefo.persistence.models.Role;
import com.genefo.persistence.models.User;
import com.genefo.persistence.models.UserStatus;
import com.genefo.services.UserService;
import com.genefo.services.utils.ValidationUtils;

/**
 * 
 * @author Alexey
 *
 */
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService<Long> {

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDao;
	
	
	@Override	
	public GenericDao<User, Long> getDAO() {
		return userDao;
	}
	
	@Override
	public Logger getServiceLogger() {
		return logger;
	}

	public User getUserByEMail(String email) {
		logger.debug("In function getUserByEMail("+ email +") ");
		if(!ValidationUtils.isEMailValid(email)) {
			logger.error("EMail "+ email +" is not valid.");
			throw new RuntimeException("EMail "+ email +" is not valid.");
		}
		User foundUser = userDao.getUserByEmail(email);
		logger.info("getUserByEmail("+ email +"): Found user = "+ foundUser);
		return foundUser;
	}

	public boolean hasExist(User user) {
		logger.debug("In function hasExist(User) ");
		User fuser = userDao.findUniqueResultByExample(user);
		boolean exist = (fuser != null);
		logger.info("hasExist("+ user +"): Result = "+ exist);
		return exist;
	}

	public User createTestUser() {

		User u1 = new User();
		u1.setLastName("LastName");
		u1.setFirstName("FirstName");
		u1.setEmail("aa@bb.com");

		Gender gender = new Gender();
		gender.setId(1);
		u1.setGender(gender);

		u1.setPassword("password");

		Role role = new Role();
		role.setId(1);
		u1.setRole(role);

		u1.setStatus(UserStatus.Created);
		return u1;		

	}

	public void addProfile(User user, Profile prof) {
		// TODO Auto-generated method stub
		
	}

	public void deleteProfile(User user, Profile prof) {
		// TODO Auto-generated method stub
		
	}

	
}
