package com.qr.model.DO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;;

@Entity
@Table(name = "account")

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "Password")
	private String password;

	@Column(name = "EmailDomain")
	private String emailDomain;

	@Column(name = "TimeZoneCity")
	private String timeZoneCity;

	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE })
	private List<Contact> contacts = new ArrayList<Contact>(0);
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE})
	private List<Profile> profiles = new ArrayList<Profile>(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getTimeZoneCity() {
		return timeZoneCity;
	}

	public void setTimeZoneCity(String timeZoneCity) {
		this.timeZoneCity = timeZoneCity;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account Details = Id: " + this.id + " Password = "+ this.password+", Name: " + this.name + ", Email: " + this.emailDomain + ", City: "
				+ this.timeZoneCity;
	}

}
