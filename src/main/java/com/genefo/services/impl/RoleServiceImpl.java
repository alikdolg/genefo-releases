package com.genefo.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.persistence.dao.RoleDAO;
import com.genefo.persistence.models.Role;
import com.genefo.services.RoleService;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
@Service("roleService")
public class RoleServiceImpl extends ReadOnlyServiceImpl<Role, Integer> implements RoleService {

	private static Logger logger = Logger.getLogger(RoleServiceImpl.class);
	
	@Autowired
	RoleDAO genderDao;
	
	@Override
	public GenericDao<Role, Integer> getDAO() {
		return genderDao;
	}

	@Override
	public Logger getServiceLogger() {
		return logger;
	}
}
