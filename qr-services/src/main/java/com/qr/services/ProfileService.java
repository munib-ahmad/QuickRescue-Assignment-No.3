package com.qr.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.qr.dao.ProfileDao;
import com.qr.dao.impl.ProfileDaoImpl;
import com.qr.model.DO.Profile;

/**
 * Session Bean implementation class ProfileService
 */
@Stateless
@LocalBean
public class ProfileService implements ProfileServiceRemote {

	private ProfileDao profileDao = new ProfileDaoImpl();
	
	@Override
	public Profile createProfile(Profile profile) {
		return profileDao.create(profile);
	}

	@Override
	public List<Profile> viewAllProfilesByAccountId(Integer accountId) {
		return profileDao.viewAllProfilessByAccountId(accountId);
	}

	@Override
	public Profile updateProfileById(Integer id, Profile newProfile) {
		return profileDao.updateById(id, newProfile);
	}

	@Override
	public Profile deleteProfileById(Integer profileId) {
		return profileDao.deleteById(profileId);
	}

}
