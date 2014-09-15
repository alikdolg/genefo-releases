package com.genefo.services;

import javax.xml.bind.JAXBException;

import com.genefo.persistence.models.xml.PhenotypeMapList;

public interface ConditionGeneMaintenanceService {
	public PhenotypeMapList getConditions();
	public void retrieveDataAndSave()  throws JAXBException;
}
