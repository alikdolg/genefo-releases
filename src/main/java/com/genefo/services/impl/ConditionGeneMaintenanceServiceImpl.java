package com.genefo.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
public class ConditionGeneMaintenanceServiceImpl implements
ConditionGeneMaintenanceService {
	private static Logger logger = Logger.getLogger(ConditionGeneMaintenanceServiceImpl.class);

	public static final String GET_CONDITIONS_URL = "http://api.omim.org/api/geneMap?phenotypeExists=true&format=xml&sequenceID=1&start=%d&limit=%d&&apiKey=%s";
	public static final String API_KEY = "022E7B02E4E5C9C00311D1FDE8E0A0B013DFEC50";
	public static final int CHUNK_SIZE = 100;
	public static final String GENE_FILE_NAME = "geneMapList.xml";

	public File fresult ;

	public JAXBContext jaxbContext;
	public JAXBContext jaxbFContext;
	public Unmarshaller jaxbUnmarshaller;
	public Unmarshaller jaxbFUnmarshaller;

	private GeneMapList fullList = new GeneMapList();

	@PostConstruct
	public void init(){
		try {
			jaxbContext = JAXBContext.newInstance(Omim.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbFContext = JAXBContext.newInstance(GeneMapList.class);
			jaxbFUnmarshaller = jaxbFContext.createUnmarshaller();
			fresult = getFileFromURL(GENE_FILE_NAME);
			retrieveDataAndSave();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public Logger getServiceLogger() {
		return logger;
	}

	public void retrieveDataAndSave() throws JAXBException {
		try {
			fullList = (GeneMapList) jaxbUnmarshaller.unmarshal(fresult);
			return;
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		//marshalingExample();
		System.out.println("************************************************");
		boolean stopReading = false;
		int nextStartIndex = 0;

		//int fetched = 0;
		while(!stopReading) {
			long stime = System.currentTimeMillis();
			Omim current = unMarshaling(calculateURL(nextStartIndex, CHUNK_SIZE));
			GeneMapList list = null;
			try {
				list = (GeneMapList)(((ArrayList<GeneMapList>)current.getListResponse().get(0).getGeneMapList()).get(0).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			if(list.getGeneMap() != null && list.getGeneMap().size() > 0) {
				fullList.addGeneMap(list.getGeneMap());			
			}
			nextStartIndex = current.getListResponse().get(0).getEndIndex() + 1 ;
			//System.out.println("Size: "+ current.getListResponse().get(0).getTotalResults()+" Index: "+current.getListResponse().get(0).getStartIndex()+" Running time: "+(System.currentTimeMillis() - stime));
			if(list.getGeneMap() == null || list.getGeneMap().size() == 0 || 
					list.getGeneMap().size() < CHUNK_SIZE) {
				stopReading = true;
			}
		}

		save(fullList, fresult);		
	}

	private Omim unMarshaling(URL url) throws JAXBException {
		Omim emps = (Omim) jaxbUnmarshaller.unmarshal( /*new File("c:/geneMap.xml")*/url );
		return emps;
	}
	
	private URL calculateURL(int fromIndex, int amount) {
		String expr = String.format(GET_CONDITIONS_URL, fromIndex, amount, API_KEY);
		URL url = null;
		try {
			url = new URL(expr);
		}catch(MalformedURLException mue) {
			mue.printStackTrace();
		}
		return url;
	}

	private void save(GeneMapList mapList, File file) throws JAXBException {
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// writing to a file
		jaxbMarshaller.marshal(mapList, file);
	}

	private File getFileFromURL(String fname) {
		File fl = null;
		try {
			fl = new ClassPathResource(fname).getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fl;
	}
	
	public PhenotypeMapList getConditions() {
		try {
			retrieveDataAndSave();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		PhenotypeMapList result_phList = new PhenotypeMapList();
		List<GeneMap> gnmapLst = fullList.getGeneMap();
		for(GeneMap currentMap: gnmapLst) {
			List<PhenotypeMapList> phMapList = currentMap.getPhenotypeMapList();
			for(PhenotypeMapList currentPhLst: phMapList) {
				List<PhenotypeMap> phList = currentPhLst.getPhenotypeMap();
				for(PhenotypeMap currentPhMap: phList) {					
					if(!result_phList.getPhenotypeMap().contains(currentPhMap)) {
						int mimnum = currentPhMap.getMimNumber();
						GeneMap gnMap = getGeneByMim(mimnum);
						if(!currentPhMap.getGeneMap().contains(gnMap)) {
							currentPhMap.getGeneMap().add(gnMap);
						}
						result_phList.getPhenotypeMap().add(currentPhMap);
					} else {
						int idx = result_phList.getPhenotypeMap().indexOf(currentPhMap);
						int mimnum = currentPhMap.getMimNumber();
						GeneMap gnMap = getGeneByMim(mimnum);
						if(!result_phList.getPhenotypeMap().get(idx).getGeneMap().contains(gnMap)) {
							result_phList.getPhenotypeMap().get(idx).getGeneMap().add(gnMap);
						}
					}
				}
			}
		}	
		return result_phList;
	}
	
	private GeneMap getGeneByMim(int mimnumber) {
		for(GeneMap gnmap: fullList.getGeneMap() ) {
			if(gnmap.getMimNumber() == mimnumber) return gnmap;
		}
		return null;
	}
}
