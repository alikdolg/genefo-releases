package com.genefo.services;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

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
@TransactionConfiguration(defaultRollback=true)
public abstract class AbstractServiceTest {

}
