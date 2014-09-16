package com.genefo.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.persistence.models.xml.GeneMap;
import com.genefo.persistence.models.xml.GeneMapList;
import com.genefo.persistence.models.xml.Omim;
import com.genefo.persistence.models.xml.PhenotypeMap;
import com.genefo.persistence.models.xml.PhenotypeMapList;
import com.genefo.services.ConditionGeneMaintenanceService;


/**
 * 
 * @author Alexey
 *
 */
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
@Service("cgMaintenanceService")
@Singleton
public class ConditionGeneMaintenanceServiceImpl implements ConditionGeneMaintenanceService {
	private static Logger logger = Logger.getLogger(ConditionGeneMaintenanceServiceImpl.class);

	public static final String GET_CONDITIONS_URL = "http://api.omim.org/api/geneMap?phenotypeExists=%s&format=xml&sequenceID=1&start=%d&limit=%d&&apiKey=%s";
	public static final String API_KEY = "022E7B02E4E5C9C00311D1FDE8E0A0B013DFEC50";
	public static final int CHUNK_SIZE = 100;
	public static final String GENE_FILE_NAME = "gene_map_list.xml";
	public static final String CONDITIONS_FILE_NAME = "phenotype_map_list.xml";
	public static final String GENE_WITHOUT_CONDITION_FILE_NAME = "gene_without_condition_map_list.xml";
	public static final String RESOURCE_DIR = "/conditions_config";

	private File conditionsFile ;
	private File geneFile ;
	private File geneWithotConditionFile ;

	private JAXBContext jaxbContext;
	private JAXBContext jaxbGeneContext;
	private JAXBContext jaxbConditionContext;
	
	private Unmarshaller jaxbUnmarshaller;
	private Unmarshaller jaxbGeneUnmarshaller;
	private Unmarshaller jaxbConditionUnmarshaller;

	private GeneMapList fullList = new GeneMapList();
	private PhenotypeMapList conditionsList = new PhenotypeMapList();
	private GeneMapList geneWithoutConditionList = new GeneMapList();

	@PostConstruct
	public void init(){
		logger.debug("In init method");
		//load data from xml - if not exist or something went wrong create xml files
		try {
			//jaxb for retrieve from site
			jaxbContext = JAXBContext.newInstance(Omim.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			//for retrieve from GENE_FILE_NAME xml
			jaxbGeneContext = JAXBContext.newInstance(GeneMapList.class);
			jaxbGeneUnmarshaller = jaxbGeneContext.createUnmarshaller();
			
			//for retrieve from CONDITIONS_FILE_NAME xml
			jaxbConditionContext = JAXBContext.newInstance(PhenotypeMapList.class);
			jaxbConditionUnmarshaller = jaxbConditionContext.createUnmarshaller();
			
			loadGenes();			
			loadConditions();
			loadGenesWithoutConditions();
			
		} catch (JAXBException e) {
			logger.error("Loading data failed.", e);
			try {
				logger.info("Trying to retrieve data from http://api.omim.org/.");
				retrieveAndSave();
			} catch (JAXBException e1) {
				logger.error("Retrieve data failed.", e);
			}
		}	
	}
	
	private void loadConditions() throws JAXBException {
		conditionsFile = getFileFromURL(CONDITIONS_FILE_NAME);
		logger.info("Loading data from file: "+ conditionsFile.getAbsolutePath());
		conditionsList = (PhenotypeMapList) jaxbConditionUnmarshaller.unmarshal(conditionsFile);
	}
	
	private void loadGenes() throws JAXBException {
		geneFile = getFileFromURL(GENE_FILE_NAME);
		logger.info("Loading data from file: "+ geneFile.getAbsolutePath());
		fullList = (GeneMapList) jaxbUnmarshaller.unmarshal(geneFile);
	}
	
	private void loadGenesWithoutConditions() throws JAXBException {
		geneWithotConditionFile = getFileFromURL(GENE_WITHOUT_CONDITION_FILE_NAME);
		logger.info("Loading data from file: "+ geneWithotConditionFile.getAbsolutePath());
		geneWithoutConditionList = (GeneMapList) jaxbGeneUnmarshaller.unmarshal(geneWithotConditionFile);
	}

	public Logger getServiceLogger() {
		return logger;
	}
	
	/**
	 * 
	 * @param withCondition
	 * @return
	 * @throws JAXBException
	 */
	private GeneMapList retrieveData(boolean withCondition) throws JAXBException {
		boolean stopReading = false;
		int nextStartIndex = 0;
		GeneMapList retrievedList = new GeneMapList();
		while(!stopReading) {
			
			Omim current = unMarshaling(calculateURL(nextStartIndex, CHUNK_SIZE, withCondition));
			GeneMapList list = null;
			try {
				list = (GeneMapList)(((ArrayList<GeneMapList>)current.getListResponse().get(0).getGeneMapList()).get(0).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			if(list.getGeneMap() != null && list.getGeneMap().size() > 0) {
				retrievedList.addGeneMap(list.getGeneMap());			
			}
			nextStartIndex = current.getListResponse().get(0).getEndIndex() + 1 ;
			if(list.getGeneMap() == null || list.getGeneMap().size() == 0 || 
					list.getGeneMap().size() < CHUNK_SIZE) {
				stopReading = true;
			}
		}
		return retrievedList;
	}
	
	/**
	 * 
	 * @throws JAXBException
	 */
	public synchronized void retrieveAndSave() throws JAXBException {
		logger.debug("In method retrieveAndSave()");
		logger.info("Retrieve Gene data");
		
		//retrieve geneList with conditions
		fullList = retrieveData(true);
		
		//save geneList as xml
		logger.info("Save Gene structure to file "+ GENE_FILE_NAME);
		save(fullList, getFileFromURL(GENE_FILE_NAME));
		
		//create conditions list from geneList with conditions
		logger.info("Calculate condition references.");
		conditionsList = buildConditions(fullList);
		
		//save conditionsList as xml
		logger.info("Save Condition structure to file "+ CONDITIONS_FILE_NAME);
		save(conditionsList, getFileFromURL(CONDITIONS_FILE_NAME));
				
		//retrieve geneList without conditions
		logger.info("Retrieve Gene without Conditions");
		geneWithoutConditionList = retrieveData(false);
		
		//save geneList without conditions as xml
		logger.info("Save Gene without Conditions structure to file "+ GENE_WITHOUT_CONDITION_FILE_NAME);
		save(geneWithoutConditionList, getFileFromURL(GENE_WITHOUT_CONDITION_FILE_NAME));	
	}
	

	private Omim unMarshaling(URL url) throws JAXBException {
		Omim emps = (Omim) jaxbUnmarshaller.unmarshal( /*new File("c:/geneMap.xml")*/url );
		return emps;
	}
	
	private URL calculateURL(int fromIndex, int amount, boolean conditionExist) {
		String expr = String.format(GET_CONDITIONS_URL, conditionExist?"true":"false", fromIndex, amount, API_KEY);
		URL url = null;
		try {
			url = new URL(expr);
		}catch(MalformedURLException mue) {
			mue.printStackTrace();
		}
		return url;
	}

	private void save(Cloneable oList, File file) throws JAXBException {
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// writing to a file
		jaxbMarshaller.marshal(oList, file);
	}

	private File getFileFromURL(String fname) {
		File fl = null;
		try {
			fl = new ClassPathResource(RESOURCE_DIR+File.separator+fname).getFile();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				fl = new File(new ClassPathResource(RESOURCE_DIR).getFile(), fname);
				fl.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		return fl;
	}
	
	/**
	 * 
	 */
	public synchronized PhenotypeMapList getConditions() {
		try {
			loadConditions();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conditionsList;
	}
	
	/**
	 * Search Gene in both connected to condition and without condition repositories
	 * @param mimnumber
	 * @return
	 */
	public synchronized GeneMap getGeneByMim(int mimnumber) {
		for(GeneMap gnmap: fullList.getGeneMap() ) {
			if(gnmap.getMimNumber() == mimnumber) return gnmap;
		}
		for(GeneMap gnmap: geneWithoutConditionList.getGeneMap() ) {
			if(gnmap.getMimNumber() == mimnumber) return gnmap;
		}
		return null;
	}
	
	private PhenotypeMapList buildConditions(GeneMapList mapList) {
		PhenotypeMapList result_phList = new PhenotypeMapList();
		List<GeneMap> gnmapLst = mapList.getGeneMap();
		for(GeneMap currentMap: gnmapLst) {
			List<PhenotypeMapList> phMapList = currentMap.getPhenotypeMapList();
			for(PhenotypeMapList currentPhLst: phMapList) {
				List<PhenotypeMap> phList = currentPhLst.getPhenotypeMap();
				for(PhenotypeMap currentPhMap: phList) {	
					PhenotypeMap clonedPhMap = null;
					try {
						clonedPhMap = (PhenotypeMap)currentPhMap.clone();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(!result_phList.getPhenotypeMap().contains(clonedPhMap)) {
						int mimnum = clonedPhMap.getMimNumber();
						GeneMap gnMap = null;
						try {
							gnMap = (GeneMap)getGeneByMim(mimnum).clone();
							gnMap.setPhenotypeMapList(null);
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(gnMap != null && !clonedPhMap.getGeneMap().contains(gnMap)) {
							clonedPhMap.getGeneMap().add(gnMap);
						}
						result_phList.getPhenotypeMap().add(clonedPhMap);
					} else {
						int idx = result_phList.getPhenotypeMap().indexOf(clonedPhMap);
						int mimnum = clonedPhMap.getMimNumber();
						GeneMap gnMap = null;
						try {
							gnMap = (GeneMap) getGeneByMim(mimnum).clone();
							gnMap.setPhenotypeMapList(null);
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(gnMap != null && !result_phList.getPhenotypeMap().get(idx).getGeneMap().contains(gnMap)) {
							result_phList.getPhenotypeMap().get(idx).getGeneMap().add(gnMap);
						}
					}
				}
			}
		}
		return result_phList;
	}

	/**
	 * 
	 */
	public synchronized GeneMapList getGenesWithoutCondition() {
		try {
			loadGenesWithoutConditions();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return geneWithoutConditionList;
	}
}
