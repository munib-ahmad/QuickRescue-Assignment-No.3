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
import com.qr.model.DO.Profile;
import com.qr.services.ProfileServiceRemote;

@Path("profile")
public class ProfileController {

	private ProfileServiceRemote profileService = (ProfileServiceRemote) ServiceManager
			.getService(ProfileServiceRemote.class);

	@GET
	@Path("{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfiles(@PathParam("accountId") int accountId) {

		JSONArray profilesJSON = new JSONArray();

		List<Profile> profiles = profileService.viewAllProfilesByAccountId(accountId);
		for (Profile profile : profiles) {
			profilesJSON.put(new JSONObject().put("id", profile.getId()).put("name", profile.getName())
					.put("city", profile.getCity()).put("country", profile.getCountry())
					.put("accountId", profile.getAccount().getId()));
		}

		return Response.status(Response.Status.OK).entity(profilesJSON.toString()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response creatProfile(Profile profile) {
		profile = profileService.createProfile(profile);
		if (profile!=null) {
			String profileJSON = new JSONObject().put("id", profile.getId()).put("name", profile.getName())
					.put("city", profile.getCity()).put("country", profile.getCountry())
					.put("accountId", profile.getAccount().getId()).toString();
			return Response.status(Response.Status.OK).entity(profileJSON).build();
		}
		else {
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfile(Profile profile) {
		profile = profileService.updateProfileById(profile.getId(), profile);
		if (profile!=null) {
			String profileJSON = new JSONObject().put("id", profile.getId()).put("name", profile.getName())
					.put("city", profile.getCity()).put("country", profile.getCountry())
					.put("accountId", profile.getAccount().getId()).toString();
			return Response.status(Response.Status.OK).entity(profileJSON).build();
		}
		else {
			return Response.status(Response.Status.OK).entity(null).build();
		}

	}

	@DELETE
	@Path("{profileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProfile(@PathParam("profileId") int profileId) {

		Profile profile = profileService.deleteProfileById(profileId);
		if (profile!=null) {
			String profileJSON = new JSONObject().put("id", profile.getId()).put("name", profile.getName())
					.put("city", profile.getCity()).put("country", profile.getCountry())
					.put("accountId", profile.getAccount().getId()).toString();
			return Response.status(Response.Status.OK).entity(profileJSON).build();
		}
		else {
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}

}
