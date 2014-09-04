package com.genefo.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.UserDAO;
import com.genefo.persistence.models.User;
import com.genefo.services.UserService;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

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
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasExist(User user) {
		logger.debug("In function hasExist(User) ");
		return userDao.findUniqueResultByExample(user) != null;
	}
	
	
	
	

}
