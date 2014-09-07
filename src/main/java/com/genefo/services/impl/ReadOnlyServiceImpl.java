package com.genefo.services.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.genefo.persistence.customdaosupport.GenericDao;
import com.genefo.services.ReadOnlyServices;

public abstract class ReadOnlyServiceImpl<T, ID extends Serializable> implements ReadOnlyServices<T>{
	public abstract GenericDao<T, ID> getDAO();
	public abstract Logger getServiceLogger();
	
	public List<T> findAll() {
		return getDAO().findAll();
	}
}
