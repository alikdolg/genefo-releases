package com.genefo.services;

import java.util.List;

/**
 * 
 * @author Alexey
 *
 * @param <T>
 */
public interface ReadOnlyServices<T> {
	public List<T> findAll();
}
