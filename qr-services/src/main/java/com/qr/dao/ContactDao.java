package com.qr.dao;

import java.util.List;

import com.qr.model.DO.Contact;

public interface ContactDao {
	Contact create(Contact contact);
	
	List<Contact> viewAllContactsByAccountId(Integer accountId);

	Contact updateById(Integer id, Contact newContact);

	Contact deleteById(Integer contactId);

}
