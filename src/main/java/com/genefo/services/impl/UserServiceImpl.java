package com.genefo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.dao.RoleDAO;
import com.genefo.persistence.dao.UserDAO;
import com.genefo.persistence.models.User;
import com.genefo.services.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private RoleDAO roleDao;
	
	public User add(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(User entity) {
		// TODO Auto-generated method stub

	}

	public void remove(User entity) {
		// TODO Auto-generated method stub

	}

	public List<User> getFirstUsers(int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getUsers(long fromId, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByEMail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
