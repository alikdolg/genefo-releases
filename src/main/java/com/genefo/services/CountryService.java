package com.genefo.services;

import java.util.List;

import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;

/**
 * 
 * @author Alexey
 *
 */
public interface CountryService extends ReadOnlyServices<Country>{

	public List<Country> getCountryByName(String name);
	
	public List<Country> getCountryByCode(String code);
}
