package com.genefo.services;

import java.util.List;

public interface ReadOnlyServices<T> {
	public List<T> findAll();
}
