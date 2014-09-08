package com.genefo.services;


/**
 * 
 * @author Alexey
 *
 * @param <T>
 */
public interface AbstractServices<T> extends ReadOnlyServices<T>{
	T add(T entity);
	void update(T entity);
	void remove(T entity);
	public void delete(T[] entities);
	public void deleteAll();
	
}
