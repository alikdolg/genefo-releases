package com.genefo.services;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.models.Profile;
import com.genefo.persistence.models.User;


/**
 * 
 * @author Alexey
 *
 */

@ContextConfiguration(locations={		
		"/test-configuration.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	ProfileService profileService;
	
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
	

	@Before
	@After
    public void cleanup() {
		userService.deleteAll();
    }
	
	
}
