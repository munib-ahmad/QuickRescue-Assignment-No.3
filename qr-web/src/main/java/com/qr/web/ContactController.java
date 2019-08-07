package com.qr.web;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.qr.manager.ServiceManager;
import com.qr.model.DO.Contact;
import com.qr.services.AlertServiceRemote;
import com.qr.services.ContactServiceRemote;

@Path("contact")
public class ContactController {

	private ContactServiceRemote contactService = (ContactServiceRemote) ServiceManager.getService(ContactServiceRemote.class);
	
	private AlertServiceRemote alertService = (AlertServiceRemote) ServiceManager.getService(AlertServiceRemote.class);

	@GET
	@Path("{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContacts(@PathParam("accountId") int accountId) {

		JSONArray contactsJSONarray = new JSONArray();

		List<Contact> contacts = contactService.viewAllContactsByAccountId(accountId);
		for (Contact contact : contacts) {
			contactsJSONarray.put(new JSONObject().put("id", contact.getId()).put("firstName", contact.getFirstName())
					.put("lastName", contact.getLastName()).put("email", contact.getEmail())
					.put("city", contact.getCity()).put("gender", contact.getGender())
					.put("status", contact.getStatus()).put("phoneNumber", contact.getPhoneNumber())
					.put("streetAddress", contact.getStreetAddress()).put("state", contact.getState())
					.put("country", contact.getCountry()).put("account",new JSONObject().put("id",contact.getAccount().getId())));
		}
		JSONObject contactsJSON = new JSONObject().put("data",contactsJSONarray); 

		return Response.status(Response.Status.OK).entity(contactsJSON.toString()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response creatContact(Contact contact) {
		contact = contactService.createContact(contact);
		if (contact != null) {
			
			boolean alertStatus = alertService.generateAlert(contact);
			
			String contactJSON = new JSONObject().put("id", contact.getId()).put("firstName", contact.getFirstName())
					.put("lastName", contact.getLastName()).put("email", contact.getEmail())
					.put("city", contact.getCity()).put("gender", contact.getGender())
					.put("status", contact.getStatus()).put("phoneNumber", contact.getPhoneNumber())
					.put("streetAddress", contact.getStreetAddress()).put("state", contact.getState())
					.put("country", contact.getCountry()).put("accountId", contact.getAccount().getId()).put("alertStatus",alertStatus).toString();
			return Response.status(Response.Status.OK).entity(contactJSON).build();
		} else {
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateContact(Contact contact) {
		contact = contactService.updateContactById(contact, contact.getId());
		if (contact != null) {
			
			boolean alertStatus = alertService.generateAlert(contact);
			
			String contactJSON = new JSONObject().put("id", contact.getId()).put("firstName", contact.getFirstName())
					.put("lastName", contact.getLastName()).put("email", contact.getEmail())
					.put("city", contact.getCity()).put("gender", contact.getGender())
					.put("status", contact.getStatus()).put("phoneNumber", contact.getPhoneNumber())
					.put("streetAddress", contact.getStreetAddress()).put("state", contact.getState())
					.put("country", contact.getCountry()).put("accountId", contact.getAccount().getId()).put("alertStatus",alertStatus).toString();
			return Response.status(Response.Status.OK).entity(contactJSON).build();
		} else {
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}

	@DELETE
	@Path("{contactId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteContact(@PathParam("contactId") int contactId) {

		Contact contact = contactService.deleteContactById(contactId);
		if (contact != null) {
			String contactJSON = new JSONObject().put("id", contact.getId()).put("firstName", contact.getFirstName())
					.put("lastName", contact.getLastName()).put("email", contact.getEmail())
					.put("city", contact.getCity()).put("gender", contact.getGender())
					.put("status", contact.getStatus()).put("phoneNumber", contact.getPhoneNumber())
					.put("streetAddress", contact.getStreetAddress()).put("state", contact.getState())
					.put("country", contact.getCountry()).put("accountId", contact.getAccount().getId()).toString();
			return Response.status(Response.Status.OK).entity(contactJSON).build();
		} else {
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}

}
