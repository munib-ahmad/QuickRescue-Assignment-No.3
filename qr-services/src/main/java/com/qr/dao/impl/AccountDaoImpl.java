package com.qr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.qr.dao.AccountDao;
import com.qr.hibernate.sessionFactory.SessionFactory;
import com.qr.model.DO.Account;

public class AccountDaoImpl implements AccountDao {

	private Session sessionObj;
	private org.hibernate.SessionFactory sessionFactoryObj = SessionFactory.getSessionFactoryObj();
	private final static Logger logger = Logger.getLogger(AccountDaoImpl.class);

	public Account create(Account account) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			Integer id = (Integer) sessionObj.save(account);
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			account.setId(id);
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
		return account;
	}

	@SuppressWarnings("unchecked")
	public List<Account> viewAll() {
		List<Account> AccountList = new ArrayList<Account>(0);
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			AccountList = sessionObj.createQuery("from Account").list();
			sessionObj.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return AccountList;

	}

	public Account findById(Integer accountId) {
		Account findAccountObj = null;
		try {
			// Getting Session Object From SessionFactory
			sessionObj = sessionFactoryObj.openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			findAccountObj = (Account) sessionObj.get(Account.class, accountId);
			sessionObj.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
				return null;
			}
		}

		return findAccountObj;

	}

	public Account update(Account account, Account newAccount) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			// Creating Transaction Entity
			Account fetchedObj = (Account) sessionObj.get(Account.class, account.getId());
			fetchedObj.setName(newAccount.getName());
			fetchedObj.setName(newAccount.getName());
			fetchedObj.setTimeZoneCity(newAccount.getTimeZoneCity());
			fetchedObj.setEmailDomain(newAccount.getEmailDomain());
			fetchedObj.setContacts(newAccount.getContacts());
			// sessionObj.saveOrUpdate(fetchedObj);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();

			logger.info("\nAccount With Id?= " + account.getId() + " Is Successfully Updated In The Database!\n");
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
		return account;

	}

	public Account deleteById(Integer accountId) {
		Account AccountObj = null;
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			AccountObj = (Account) sessionObj.get(Account.class, accountId);
			sessionObj.delete(AccountObj);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nAccount With Id?= " + accountId + " Is Successfully Deleted From The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
				return null;
			}
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}

		}
		return AccountObj;
	}

}
