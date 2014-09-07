package com.genefo.services;

import java.util.List;

/**
 * 
 * @author Alexey
 *
 * @param <T>
 */
public interface AbstractServices<T> {
	T add(T entity);
	void update(T entity);
	void remove(T entity);
	public void delete(T[] entities);
	public void deleteAll();
	public List<T> findAll();
}
