package com.genefo.services;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Alexey
 *
 * @param <T>
 */
public interface ReadOnlyServices<T, ID extends Serializable> {
	public List<T> findAll();
	public T getByID(ID id);
}
