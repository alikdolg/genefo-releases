package com.genefo.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.genefo.persistence.models.City;
import com.genefo.persistence.models.Country;
import com.genefo.persistence.models.Region;

/**
 * 
 * @author Alexey
 *
 */
public class LocationServiceTest extends AbstractServiceTest{

	@Autowired
	RegionService regionService;
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	CityService cityService;
	
	
	@Test
	public void testGetAllCountries() {
		List<Country> countries = countryService.findAll();
		Assert.assertNotNull(countries);
		Assert.assertTrue(countries.size() > 0);
	}
	
	@Test
	public void testGetRegionsByCountry() {
		List<Country> countries = countryService.findAll();
		List<Region> regions = countries.get(3).getRegions();
		
		Assert.assertNotNull(regions);
		Assert.assertTrue(regions.size() > 0);
	}
	
	@Test
	public void testCityByCountry() {
		List<Country> countries = countryService.findAll();
		Country country = countries.get(10);
		Hibernate.initialize(country.getCities());
		List<City> cities = country.getCities();		
		Assert.assertNotNull(cities);
		Assert.assertTrue(cities.size() > 0);
	}
	
	@Test
	public void testCityByCountryAndRegion() {
		//get all countries
		List<Country> countries = countryService.findAll();
		//select one of country - for example with index 10
		Country country = countries.get(10);
		//get all regions from this country
		List<Region> regions = country.getRegions();
		//select one region - for example index 0
		Region oneSelected = regions.get(0);
		//get all cities from selected region
		List<City> cities = oneSelected.getCities();
		Assert.assertNotNull(cities);
		Assert.assertTrue(cities.size() > 0);
	}

}
