package com.genefo.services;

import javax.xml.bind.JAXBException;

import com.genefo.persistence.models.xml.GeneMap;
import com.genefo.persistence.models.xml.GeneMapList;
import com.genefo.persistence.models.xml.PhenotypeMapList;

public interface ConditionGeneMaintenanceService {
	public PhenotypeMapList getConditions();
	public GeneMapList getGenesWithoutCondition();
	public GeneMap getGeneByMim(int mimnumber);
	public void retrieveAndSave()  throws JAXBException;
}
