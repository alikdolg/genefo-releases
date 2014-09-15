package com.genefo.services;

import com.genefo.persistence.models.Profile;
import com.genefo.persistence.models.User;


/**
 * 
 * @author Alexey
 *
 */
public interface UserService extends AbstractServices<User> {

	User getUserByEMail(String email);
	boolean hasExist(User user);
	User createTestUser();
	void addProfile(User user, Profile  prof);
	void deleteProfile(User user, Profile prof);
}
