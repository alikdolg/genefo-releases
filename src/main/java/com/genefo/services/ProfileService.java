package com.genefo.services;

import java.io.Serializable;
import java.util.List;

import com.genefo.persistence.models.Conditions;
import com.genefo.persistence.models.Profile;

/**
 * 
 * @author Alexey
 *
 */
public interface ProfileService<ID extends Serializable> extends AbstractServices<Profile, ID> {
	Profile createTestProfile();
	public void addCondition(Profile profile, Conditions conditions);
	public void setConditions(Profile profile, List<Conditions> conditions);
}
