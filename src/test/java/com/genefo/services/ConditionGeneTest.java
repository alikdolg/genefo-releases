package com.genefo.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConditionGeneTest extends AbstractServiceTest{

	@Autowired
	ConditionGeneMaintenanceService geneServe;
	
	@Test
	public void test() {

		geneServe.getConditions();

	}
	@Test
	public void test1() {

		geneServe.getConditions();

	}

}
