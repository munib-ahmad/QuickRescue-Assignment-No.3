package com.qr.services;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.qr.dao.ProfileDao;
import com.qr.dao.impl.ProfileDaoImpl;
import com.qr.model.DO.Contact;
import com.qr.model.DO.Profile;

/**
 * Session Bean implementation class HelloBean
 */
@Stateless
@Remote
public class AlertService implements AlertServiceRemote{

	private ProfileDao profileDao = new ProfileDaoImpl();

	@Override
	public boolean generateAlert(Contact contact) {
		List<Profile> profileList = profileDao.viewAllProfilessByAccountId(contact.getAccount().getId());
		for(Profile profile:profileList) {
			if(contact.getCountry().equalsIgnoreCase(profile.getCountry())||contact.getCity().equalsIgnoreCase(profile.getCity())) {
				return true;
			}
		}
		return false;
	}

}
