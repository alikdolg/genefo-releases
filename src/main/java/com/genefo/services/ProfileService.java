package com.genefo.services;

import com.genefo.persistence.models.Profile;

public interface ProfileService extends AbstractServices<Profile> {
	Profile createTestProfile();
}
