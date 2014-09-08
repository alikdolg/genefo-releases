package com.genefo.services;

import java.util.List;

import com.genefo.persistence.models.City;
import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;

/**
 * 
 * @author Alexey
 *
 */
public interface CityService extends ReadOnlyServices<City>{

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
