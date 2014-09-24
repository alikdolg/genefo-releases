package com.genefo.services;

import java.io.Serializable;

import com.genefo.persistence.models.Race;

/**
 * 
 * @author Alexey
 *
 */
public interface RaceService<ID extends Serializable> extends ReadOnlyServices<Race, ID>{

}
