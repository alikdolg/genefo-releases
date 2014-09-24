package com.genefo.services;

import java.io.Serializable;
import java.util.List;

import com.genefo.persistence.models.Country;

/**
 * 
 * @author Alexey
 *
 */
public interface CountryService<ID extends Serializable> extends ReadOnlyServices<Country, ID>{

	public List<Country> getCountryByName(String name);
	
	public List<Country> getCountryByCode(String code);
}
