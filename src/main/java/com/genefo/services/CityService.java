package com.genefo.services;

import java.io.Serializable;
import java.util.List;

import com.genefo.persistence.models.City;
import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;

/**
 * 
 * @author Alexey
 *
 */
public interface CityService<ID extends Serializable> extends ReadOnlyServices<City, ID>{

	/**
	 * 
	 * @param country
	 * @return
	 */
	List<City> getCitiesByCountry(Country country);
	
	/**
	 * 
	 * @param country
	 * @param region
	 * @return
	 */
	List<City> getCitiesByCountryAndRegion(Country country, Region region);
}
