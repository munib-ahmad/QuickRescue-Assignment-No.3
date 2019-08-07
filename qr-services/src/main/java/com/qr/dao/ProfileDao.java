package com.qr.dao;

import java.util.List;

import com.qr.model.DO.Profile;

public interface ProfileDao {
	
	 Profile create(Profile profile);
	
	List<Profile> viewAllProfilessByAccountId(Integer accountId);

	Profile updateById(Integer id, Profile newProfile);

	Profile deleteById(Integer profileId);
	
}
