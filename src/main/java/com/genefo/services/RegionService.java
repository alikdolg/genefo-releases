package com.genefo.services;

import java.util.List;

import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;

/**
 * 
 * @author Alexey
 *
 */
public interface RegionService extends ReadOnlyServices<Region>{

	/**
	 * 
	 * @param country
	 * @return
	 */
	public List<Region> getRegionsByCountry(Country country);
}
