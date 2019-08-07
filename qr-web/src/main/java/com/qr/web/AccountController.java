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
import com.qr.model.DO.Account;
import com.qr.services.AccountServiceRemote;

@Path("account")
public class AccountController {

	private AccountServiceRemote accountService = (AccountServiceRemote) ServiceManager
			.getService(AccountServiceRemote.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccounts() {

		JSONArray accountsJSON = new JSONArray();

		List<Account> accounts = accountService.viewAllAccounts();
		for (Account account : accounts) {
			accountsJSON.put(new JSONObject().put("id", account.getId()).put("name", account.getName())
					.put("email", account.getEmailDomain()).put("timeZoneCity", account.getTimeZoneCity()));
		}

		return Response.status(Response.Status.OK).entity(accountsJSON.toString()).build();
	}

	@GET
	@Path("{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnAccount(@PathParam("accountId") int accountId) {

		Account account = accountService.findAccountById(accountId);
		if (account != null) {
			String accountJSON = new JSONObject().put("id", account.getId()).put("password", account.getPassword())
					.put("name", account.getName()).put("email", account.getEmailDomain())
					.put("timeZoneCity", account.getTimeZoneCity()).toString();
			return Response.status(Response.Status.OK).entity(accountJSON).build();

		} else {
			return Response.status(Response.Status.OK).entity(null).build();
		}

	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response creatAccount(Account account) {

		account = accountService.createAccount(account);
		if (account != null) {
			String accountJSON = new JSONObject().put("id", account.getId()).put("name", account.getName())
					.put("email", account.getEmailDomain()).put("timeZoneCity", account.getTimeZoneCity()).toString();
			return Response.status(Response.Status.OK).entity(accountJSON).build();

		} else {
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Account account) {
		if (account != null) {
			if (!account.getEmailDomain().trim().equals("") && !account.getPassword().trim().equals("")) {
				account = accountService.checkLogin(account);
				if (account != null) {
					String accountJSON = new JSONObject().put("id", account.getId()).put("name", account.getName())
							.put("email", account.getEmailDomain()).put("timeZoneCity", account.getTimeZoneCity())
							.put("login", true).toString();
					return Response.status(Response.Status.OK).entity(accountJSON).build();
				} else {
					return Response.status(Response.Status.OK).entity("{\"login\":false}").build();
				}
			} else {
				return Response.status(Response.Status.OK).entity("{\"login\":false}").build();
			}
		} else {
			return Response.status(Response.Status.OK).entity("{\"login\":false}").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(Account account) {
		account = accountService.updateAccount(account, account);
		if (account != null) {
			String accountJSON = new JSONObject().put("id", account.getId()).put("name", account.getName())
					.put("email", account.getEmailDomain()).put("timeZoneCity", account.getTimeZoneCity()).toString();
			return Response.status(Response.Status.OK).entity(accountJSON).build();

		} else {
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}

	@DELETE
	@Path("{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAccount(@PathParam("accountId") int accountId) {

		Account account = accountService.deleteAccountById(accountId);
		if (account != null) {
			String accountJSON = new JSONObject().put("id", account.getId()).put("name", account.getName())
					.put("email", account.getEmailDomain()).put("timeZoneCity", account.getTimeZoneCity()).toString();
			return Response.status(Response.Status.OK).entity(accountJSON).build();

		} else {
			return Response.status(Response.Status.OK).entity(null).build();
		}

	}

}