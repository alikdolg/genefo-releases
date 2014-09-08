package com.genefo.services;

import com.genefo.persistence.models.Profile;

/**
 * 
 * @author Alexey
 *
 */
public interface ProfileService extends AbstractServices<Profile> {
	Profile createTestProfile();
}
