package com.qr.services;

import java.util.List;

import javax.ejb.Remote;

import com.qr.model.DO.Profile;

@Remote
public interface ProfileServiceRemote {
	Profile createProfile(Profile profile);
		
	List<Profile> viewAllProfilesByAccountId(Integer accountId);

	Profile updateProfileById(Integer id, Profile newProfile);

	Profile deleteProfileById(Integer profileId);
}
