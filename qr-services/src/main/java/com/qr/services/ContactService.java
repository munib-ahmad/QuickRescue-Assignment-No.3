package com.qr.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.qr.dao.ContactDao;
import com.qr.dao.impl.ContactDaoImpl;
import com.qr.model.DO.Contact;

/**
 * Session Bean implementation class ContactService
 */
@Stateless
@LocalBean
public class ContactService implements ContactServiceRemote {

	private ContactDao contactDao = new ContactDaoImpl();

	@Override
	public Contact createContact(Contact contact) {
		return contactDao.create(contact);

	}

	@Override
	public Contact updateContactById(Contact contact, Integer id) {
		return contactDao.updateById(id, contact);
	}

	@Override
	public Contact deleteContactById(Integer contactId) {
		return contactDao.deleteById(contactId);
	}

	@Override
	public List<Contact> viewAllContactsByAccountId(Integer accountId) {
		return contactDao.viewAllContactsByAccountId(accountId);
	}

}
