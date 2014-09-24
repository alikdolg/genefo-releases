package com.genefo.services;

import java.io.Serializable;



/**
 * 
 * @author Alexey
 *
 * @param <T>
 */
public interface AbstractServices<T, ID extends Serializable> extends ReadOnlyServices<T, ID>{
	T add(T entity);
	void update(T entity);
	void remove(T entity);
	public void delete(T[] entities);
	public void deleteAll();
	
}
