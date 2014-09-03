package com.genefo.services;

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
}
