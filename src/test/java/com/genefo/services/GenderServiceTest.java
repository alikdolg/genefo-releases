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

/**
 * 
 * @author Alexey
 *
 */
public class GenderServiceTest extends AbstractServiceTest{

	@Autowired
	GenderService genderService;
	
	@Test
	public void testGetAllGenders() {
		List<Gender> genders = genderService.findAll();
		Assert.assertNotNull(genders);		
		Assert.assertFalse(genders.size() == 0);
	}
	
}
