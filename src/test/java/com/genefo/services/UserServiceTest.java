package com.genefo.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.genefo.persistence.models.Profile;
import com.genefo.persistence.models.User;


/**
 * 
 * @author Alexey
 *
 */

public class UserServiceTest extends AbstractServiceTest{

	@Autowired
	UserService<Long> userService;
	
	@Autowired
	ProfileService<Long> profileService;
	
	@Test
	public void testCreateNewUser() {
		User defUser = userService.createTestUser();
		User resUser = userService.add(defUser);
		Assert.assertNotNull(resUser);
		Assert.assertEquals(defUser, resUser);
	}
	
	@Test
	public void testHasExist() {
		User defUser = userService.createTestUser();
		User resUser = userService.add(defUser);
		Assert.assertTrue(userService.hasExist(resUser)); 
	}
	
	
	@Test
	public void testGetUserByEMail() {
		User defUser = userService.createTestUser();
		User resUser = userService.add(defUser);
		resUser = userService.getUserByEMail("aa@bb.com");
		Assert.assertNotNull(resUser);
	}

	
	@Test
	public void testDeleteUser() {
		User defUser = userService.createTestUser();
		User resUser = userService.add(defUser);
		userService.remove(resUser);
	}
	
	@Test
	public void testCreateNewUserWithProfile() {
		User defUser = userService.createTestUser();
		User resUser = userService.add(defUser);
		Profile prof = profileService.createTestProfile();
		resUser.addProfile(prof);
		profileService.add(prof);
		
		resUser = userService.getUserByEMail(resUser.getEmail());
		Assert.assertNotNull(resUser);
		Assert.assertNotNull(resUser.getProfiles());
		Assert.assertEquals(1, resUser.getProfiles().size());
	}
	
	@Test
	public void testUpdateUser() {
		User defUser = userService.createTestUser();
		User resUser = userService.add(defUser);
		
		//Update user
		userService.getByID(529l);
		User findU = userService.getUserByEMail("aa@bb.com");
		
		findU.setFirstName("Alexey");
		userService.update(findU);
	}
	

	@Before
	@After
    public void cleanup() {
		userService.deleteAll();
    }
	
	
}
