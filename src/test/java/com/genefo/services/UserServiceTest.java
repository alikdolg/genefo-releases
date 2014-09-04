package com.genefo.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
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

import com.genefo.persistence.models.Gender;
import com.genefo.persistence.models.Role;
import com.genefo.persistence.models.User;
import com.genefo.persistence.models.UserStatus;


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
	
	@Test
	public void testCreateNewUser() {
		User defUser = createDefaultUser();
		User resUser = userService.add(defUser);
		Assert.assertNotNull(resUser);
		Assert.assertEquals(defUser, resUser);
	}
	
	@Test
	@DependsOn("com.genefo.services.UserServiceTest#testCreateNewUser")
	public void testHasExist() {
		User defUser = createDefaultUser();
		Assert.assertTrue(userService.hasExist(defUser)); 
	}
	
	@Test
	@DependsOn("com.genefo.services.UserServiceTest#testCreateNewUser")
	public void testDeleteUser() {
		User defUser = createDefaultUser();
		userService.remove(defUser);
	}

	private User createDefaultUser() {
		User u1 = new User();
		u1.setLastName("LastName");
		u1.setFirstName("FirstName");
		u1.setEmail("EMail");

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

}
