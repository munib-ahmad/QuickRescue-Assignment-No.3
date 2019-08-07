package com.qr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.qr.dao.ContactDao;
import com.qr.hibernate.sessionFactory.SessionFactory;
import com.qr.model.DO.Contact;

public class ContactDaoImpl implements ContactDao {

	private Session sessionObj;
	private org.hibernate.SessionFactory sessionFactoryObj = SessionFactory.getSessionFactoryObj();
	private final static Logger logger = Logger.getLogger(ContactDaoImpl.class);

	public Contact create(Contact contact) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();
			sessionObj.save(contact);

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
		return contact;

	}

	public Contact updateById(Integer id, Contact newContact) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			// Creating Transaction Entity
			Contact contactObj = (Contact) sessionObj.get(Contact.class, id);
			contactObj.setFirstName(newContact.getFirstName());
			contactObj.setLastName(newContact.getLastName());
			contactObj.setEmail(newContact.getEmail());
			contactObj.setGender(newContact.getGender());
			contactObj.setPhoneNumber(newContact.getPhoneNumber());
			contactObj.setStatus(newContact.getStatus());
			contactObj.setStreetAddress(newContact.getStreetAddress());
			contactObj.setCity(newContact.getCity());
			contactObj.setState(newContact.getState());
			contactObj.setCountry(newContact.getCountry());
			contactObj.setAccount(newContact.getAccount());

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nContact With Id?= " + id + " Is Successfully Updated In The Database!\n");
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
		return newContact;

	}

	public Contact deleteById(Integer contactId) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			Contact contactObj = (Contact) sessionObj.get(Contact.class, contactId);
			sessionObj.delete(contactObj);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nContact With Id?= " + contactId + " Is Successfully Deleted From The Database!\n");
			return contactObj;
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

	@SuppressWarnings("unchecked")
	public List<Contact> viewAllContactsByAccountId(Integer accountId) {
		List<Contact> contactList = new ArrayList<Contact>(0);
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

//			Criteria crit = sessionObj.createCriteria(Contact.class);
//			crit.add(Restrictions.eq("AccountId", accountId));
//			contactList = crit.list();
			String hql = "FROM Contact where AccountId = :accountId";
			org.hibernate.Query query = sessionObj.createQuery(hql);
			query.setParameter("accountId", accountId);
			contactList = query.list();

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
		return contactList;

	}

}
