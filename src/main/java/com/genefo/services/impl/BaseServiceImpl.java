package com.genefo.services.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genefo.services.AbstractServices;

/**
 * 
 * @author Alexey
 *
 * @param <T>
 * @param <ID>
 */
@Service
@Transactional
public abstract class BaseServiceImpl<T, ID extends Serializable> extends ReadOnlyServiceImpl<T, ID>
	implements AbstractServices<T, ID> {

	/*
	 * (non-Javadoc)
	 * @see com.genefo.services.AbstractServices#add(java.lang.Object)
	 */
	public T add(T entity) {
		//check exists
		getServiceLogger().debug("In function add("+entity.getClass().getName()+") ");
		if(getDAO().findByExample(entity).size() > 0) {
			throw new RuntimeException("Item "+ entity + " already exist. Use Update instead.");
		}
		//add
		T addedEntity = getDAO().create(entity);
		getServiceLogger().info("Item "+ entity + " added successfully.");
		return addedEntity;
	}

	/*
	 * (non-Javadoc)
	 * @see com.genefo.services.AbstractServices#update(java.lang.Object)
	 */
	public void update(T entity) {
		//check exists
		getServiceLogger().debug("In function update("+entity.getClass().getName()+") ");
		T currentEntity = getDAO().findById( getDAO().getID(entity));
		if(currentEntity == null) {
			throw new RuntimeException("Update failed. Item "+ entity + " does not exist.");
		}
		
		//update
		getDAO().update(entity);
		getServiceLogger().info("Item "+ entity + " updated successfully.");
	}

	/*
	 * (non-Javadoc)
	 * @see com.genefo.services.AbstractServices#remove(java.lang.Object)
	 */
	public void remove(T entity) {
		//check exists
		getServiceLogger().debug("In function delete("+entity.getClass().getName()+") ");
		T resEntity = getDAO().findUniqueResultByExample(entity);
		if(resEntity == null) {
			throw new RuntimeException("Delete failed. Item "+ entity + " does not exist.");
		}
		//delete
		getDAO().delete(resEntity);
		getServiceLogger().info("Item "+ resEntity + " deleted successfully.");

	}
	
	public void delete(T[] entities) {
		getServiceLogger().debug("In function delete("+entities.getClass().getName()+") ");
		getDAO().delete(entities);		
	}

	public void deleteAll() {
		getServiceLogger().debug("In function deleteAll() ");
		getDAO().delete(findAll());
	}
}
