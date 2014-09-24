package com.genefo.services.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.services.ReadOnlyServices;

/**
 * 
 * @author Alexey
 *
 * @param <T>
 * @param <ID>
 */
public abstract class ReadOnlyServiceImpl<T, ID extends Serializable> implements ReadOnlyServices<T, ID>{
	public abstract GenericDao<T, ID> getDAO();
	public abstract Logger getServiceLogger();
	
	public List<T> findAll() {
		getServiceLogger().debug("In function findAll() ");
		return getDAO().findAll();
	}
	
	public T getByID(ID id) {
		getServiceLogger().debug("getByID() ");
		return getDAO().findById(id);
	}	
	
}
