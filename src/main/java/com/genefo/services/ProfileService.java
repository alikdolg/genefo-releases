package com.genefo.services;

import com.genefo.persistence.models.Profile;
import com.genefo.persistence.models.User;

public interface ProfileService extends AbstractServices<Profile> {
	Profile createTestProfile();
}
