package com.genefo.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.genefo.persistence.models.xml.GeneMap;

public class ConditionGeneTest extends AbstractServiceTest{

	@Autowired
	ConditionGeneMaintenanceService geneServer;
	
	@Test
	public void testGetConditions() {
		geneServer.getConditions();

	}
	@Test
	public void testGetGenesWithoutConditions() {
		geneServer.getGenesWithoutCondition();
	}
	
	@Test
	public void testGetGeneByMIM() {
		GeneMap gene = geneServer.getGeneByMim(600217);
	}

}
