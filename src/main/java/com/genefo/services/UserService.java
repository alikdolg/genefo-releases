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
	List<User> getFirstUsers(int size);
	List<User> getUsers(long fromId, int size);
	User getUserByEMail(String email);
	
//	void createUser(User user);
//	void updateUser(User user);
//	void deleteUser(User user);
	
	
}
