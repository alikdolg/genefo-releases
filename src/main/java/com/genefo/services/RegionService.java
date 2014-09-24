package com.genefo.services;

import java.io.Serializable;
import java.util.List;

import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;

/**
 * 
 * @author Alexey
 *
 */
public interface RegionService<ID extends Serializable> extends ReadOnlyServices<Region, ID>{

	/**
	 * 
	 * @param country
	 * @return
	 */
	public List<Region> getRegionsByCountry(Country country);
}
