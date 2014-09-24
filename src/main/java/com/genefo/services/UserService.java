package com.genefo.services;

import java.io.Serializable;

import com.genefo.persistence.models.Profile;
import com.genefo.persistence.models.User;


/**
 * 
 * @author Alexey
 *
 */
public interface UserService<ID extends Serializable> extends AbstractServices<User, ID> {

	User getUserByEMail(String email);
	boolean hasExist(User user);
	User createTestUser();
	void addProfile(User user, Profile  prof);
	void deleteProfile(User user, Profile prof);
}
