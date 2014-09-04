package com.genefo.services;

import java.util.List;

import com.genefo.persistence.models.User;

/**
 * 
 * @author Alexey
 *
 */
public interface UserService extends AbstractServices<User> {

	//List<User> geAlltUsers();
	User getUserByEMail(String email);
	boolean hasExist(User user);
}
