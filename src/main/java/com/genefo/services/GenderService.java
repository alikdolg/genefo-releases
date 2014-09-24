package com.genefo.services;

import java.io.Serializable;

import com.genefo.persistence.models.Gender;

/**
 * 
 * @author Alexey
 *
 */
public interface GenderService<ID extends Serializable> extends ReadOnlyServices<Gender, ID> {

}
