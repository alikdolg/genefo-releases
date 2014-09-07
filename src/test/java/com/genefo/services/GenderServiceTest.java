package com.genefo.services;

import java.sql.SQLException;
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
import com.genefo.persistence.models.Region;

@ContextConfiguration(locations={		
		"/test-configuration.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class GenderServiceTest {

	@Autowired
	GenderService genderService;
	
	@Test
	public void testGetAllGenders() {
		List<Gender> genders = genderService.findAll();
		Assert.assertNotNull(genders);		
		Assert.assertFalse(genders.size() == 0);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGenderUpdate() {
		Gender gentest = genderService.findAll().get(0);
		gentest.setDescription("Desc test");
		genderService.update(gentest);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGenderDeleteAll() {
		genderService.deleteAll();
	}
}
