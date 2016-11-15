/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.openlimit.ExampleApplication.ExampleBackend.RestExample;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.openlimit.ExampleApplication.ExampleBackend.JPAExample.ManagementServiceImpl;
import com.openlimit.ExampleApplication.ExampleBackend.JPAExample.Team;
import com.openlimit.ExampleApplication.ExampleBackend.JPAExample.User;

@Produces(MediaType.TEXT_HTML)
@Path("/")
public class RestService {
	@Inject
	HelloService helloService;

	@GET
	@Path("/json")
	@Produces({ "application/json" })
	public String getHelloWorldJSON() {
		return "{\"result\":\"" + helloService.createHelloMessage("World") + "\"}";
	}

	@GET
	@Path("/xml")
	@Produces({ "application/xml" })
	public String getHelloWorldXML() {
		return "<xml><result>" + helloService.createHelloMessage("World") + "</result></xml>";
	}

	@GET
	@Path("/wurst")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getHelloWorldWurst() {
		return helloService.createHelloMessage("Wurst");
	}

	@GET
	@Path("/worst")
	public String getHelloWorldWorst() {
		return "<h1>" + helloService.createHelloMessage("Wurst") + "</h1>";
	}

	private String userString = "";
	@GET
    @Path("/users")
    @Produces({ MediaType.TEXT_PLAIN })
    public String getUsernames() {
    	
    	ManagementServiceImpl msi = new ManagementServiceImpl();
    	List<User> users = msi.getUsernames();
    
    	users.forEach(u -> {
    		userString = userString + u.getUsername() + "; ";
    	});
    	
        return userString;
    }

	private String teamString = "";
	@GET
	@Path("/teams")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getTeams() {

		ManagementServiceImpl msi = new ManagementServiceImpl();
		List<Team> teams = msi.getTeams();
	    
    	teams.forEach(t -> {
    		teamString = teamString + t.getName() + "; ";
    	});
    	
        return teamString;
	}

	@POST
	@Path("/addUser")
	@Consumes("text/plain")
	public Response addUser(String name) {

		ManagementServiceImpl msi = new ManagementServiceImpl();

		String result = msi.addUser(name);
		return Response.status(201).entity(result).build();

	}

	private String userJson = "";
	@POST
	@Path("/getUserByName")
	@Consumes("application/json")
	public String getUserByName(String name) {

		ManagementServiceImpl msi = new ManagementServiceImpl();

		User user = msi.getUserByName(name);
		
		Gson gson = new Gson();
		userJson = gson.toJson(user);
		
		return userJson;

	}

	@POST
	@Path("/updateUser")
	@Consumes("application/json")
	public Response updateUser(String json) {

		ManagementServiceImpl msi = new ManagementServiceImpl();

		Gson gson = new Gson();
		User user = gson.fromJson(json, User.class);
		
		String result = msi.updateUser(user);
		return Response.status(201).entity(result).build();

	}
	
	@POST
	@Path("/deleteUser")
	@Consumes("text/plain")
	public Response deleteUser(String name) {

		ManagementServiceImpl msi = new ManagementServiceImpl();
		
		try {
			msi.deleteUser(name);
			return Response.status(201).entity("success").build();
		} catch (AssertionError e) {
			return Response.status(201).entity("failed").build();
		}

	}
}
