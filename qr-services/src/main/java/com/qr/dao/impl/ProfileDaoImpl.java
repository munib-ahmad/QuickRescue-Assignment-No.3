package com.qr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.qr.dao.ProfileDao;
import com.qr.hibernate.sessionFactory.SessionFactory;
import com.qr.model.DO.Contact;
import com.qr.model.DO.Profile;

public class ProfileDaoImpl implements ProfileDao {
	
	private Session sessionObj;
	private org.hibernate.SessionFactory sessionFactoryObj = SessionFactory.getSessionFactoryObj();
	private final static Logger logger = Logger.getLogger(ProfileDaoImpl.class);

	public Profile create(Profile profile) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();
			sessionObj.save(profile);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nSuccessfully Created Records In The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			return null;
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return profile;
	}

	public List<Profile> viewAllProfilessByAccountId(Integer accountId) {
		List<Profile> profileList = new ArrayList<Profile>(0);
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();
			String hql = "FROM Profile where AccountId = :accountId";
			org.hibernate.Query query = sessionObj.createQuery(hql);
			query.setParameter("accountId", accountId);
			profileList = query.list();

		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			return null;
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return profileList;

	}

	public Profile updateById(Integer id, Profile newProfile) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			// Creating Transaction Entity
			Profile profileObj = (Profile) sessionObj.get(Profile.class, id);
			profileObj.setName(newProfile.getName());
			profileObj.setCity(newProfile.getCity());
			profileObj.setCountry(newProfile.getCountry());
			profileObj.setAccount(newProfile.getAccount());

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nProfile With Id?= " + id + " Is Successfully Updated In The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			return null;
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return newProfile;

	}

	public Profile deleteById(Integer profileId) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			Profile profileObj = (Profile) sessionObj.get(Profile.class, profileId);
			sessionObj.delete(profileObj);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nProfile With Id?= " + profileId + " Is Successfully Deleted From The Database!\n");
			return profileObj;
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			return null;
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
	}

}
