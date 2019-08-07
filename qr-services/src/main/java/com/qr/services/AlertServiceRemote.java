package com.qr.services;

import javax.ejb.Remote;

import com.qr.model.DO.Contact;

@Remote
public interface AlertServiceRemote {
	boolean generateAlert(Contact contact);
}
