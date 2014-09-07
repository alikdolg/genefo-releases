package com.genefo.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.models.Gender;
import com.genefo.persistence.models.Role;

@ContextConfiguration(locations={		
		"/test-configuration.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class RoleServiceTest {

	@Autowired
	RoleService roleService;
	
	@Test
	public void testGetAllRoles() {
		List<Role> roles = roleService.findAll();
		Assert.assertNotNull(roles);		
		Assert.assertFalse(roles.size() == 0);
	}
	
	
}
