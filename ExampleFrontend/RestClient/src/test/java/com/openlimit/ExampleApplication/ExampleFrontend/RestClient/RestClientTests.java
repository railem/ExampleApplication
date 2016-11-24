package com.openlimit.ExampleApplication.ExampleFrontend.RestClient;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;

import com.openlimit.ExampleApplication.ExampleCommon.common.Team;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.Header.header;
import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.matchers.Times.exactly;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockserver.model.HttpForward.Scheme.HTTP;
import static org.mockserver.model.HttpStatusCode.ACCEPTED_202;

import java.io.FileNotFoundException;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestClientTests {
	
	private ClientAndServer mockServer;
	private RestClient rc;
	
	@Before
	public void init() {
		mockServer = startClientAndServer(5000);
//		MockServerClient mockClient = new MockServerClient("127.0.0.1", 5000);
//		
//	    mockClient.when(
//	            request()
//	                    .withPath("/RestExample/rest/users")
//	                    .withMethod("GET")
//	    ).respond(
//	            response()
//	                    .withStatusCode(200)
//	                    .withHeaders(
//                                new Header("Content-Type", "application/json; charset=utf-8")
//                        )
//                        .withBody("[{\"id\":1,\"username\":\"j.gurke\",\"email\":\"jochen@gurke.de\"},{\"id\":2,\"username\":\"r.iven\",\"email\":\"x.x.x.x\"}]")
//                        .withDelay(new Delay(SECONDS, 1))
//	    );
//	    mockClient.when(
//	            request()
//	                    .withPath("/RestExample/rest/teams")
//	                    .withMethod("GET")
//	    ).respond(
//	            response()
//	                    .withStatusCode(200)
//	                    .withHeaders(
//                                new Header("Content-Type", "application/json; charset=utf-8")
//                        )
//                        .withBody("[{\"teamId\":1,\"name\":\"WurstTeam\",\"users\":[{\"id\":1,\"username\":\"j.gurke\",\"email\":\"jochen@gurke.de\"},{\"id\":2,\"username\":\"r.iven\",\"email\":\"x.x.x.x\"}]},{\"teamId\":2,\"name\":\"TeamTratsch\",\"users\":[{\"id\":3,\"username\":\"k.arl\",\"email\":\"karl@arl.com\"},{\"id\":4,\"username\":\"j.spargel\",\"email\":\"juergen@spargel.de\"},{\"id\":5,\"username\":\"j.lauch\",\"email\":\"jacob@lauch.de\"}]}]")
//                        .withDelay(new Delay(SECONDS, 1))
//	    );
	    
	    rc = new RestClient("http", "localhost", 5000, "RestExample/rest" );
	}
	
	@After
	public void close() {
		mockServer.stop();
	}
	
	@Test
	public void test01_requestUsers_success() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/users")
	                    .withMethod("GET")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "application/json; charset=utf-8")
                        )
                        .withBody("[{\"id\":1,\"username\":\"j.gurke\",\"email\":\"jochen@gurke.de\"},{\"id\":2,\"username\":\"r.iven\",\"email\":\"x.x.x.x\"}]")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		List<User> users = (List<User>) rc.getUserAsList();
		assertTrue(users.get(0).getUsername().equals("j.gurke"));
	}
	
	@Test
	public void test02_requestTeams_success() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/teams")
	                    .withMethod("GET")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "application/json; charset=utf-8")
                        )
                        .withBody("[{\"teamId\":1,\"name\":\"WurstTeam\",\"users\":[{\"id\":1,\"username\":\"j.gurke\",\"email\":\"jochen@gurke.de\"},{\"id\":2,\"username\":\"r.iven\",\"email\":\"x.x.x.x\"}]},{\"teamId\":2,\"name\":\"TeamTratsch\",\"users\":[{\"id\":3,\"username\":\"k.arl\",\"email\":\"karl@arl.com\"},{\"id\":4,\"username\":\"j.spargel\",\"email\":\"juergen@spargel.de\"},{\"id\":5,\"username\":\"j.lauch\",\"email\":\"jacob@lauch.de\"}]}]")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		List<Team> teams = (List<Team>) rc.getTeamList();
		assertTrue(teams.get(0).getName().equals("WurstTeam")); 
	}
	
	@Test
	public void test03_requestUser_success() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/getUserByName")
	                    .withMethod("POST")
	                    .withBody("j.gurke")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "application/json; charset=utf-8")
                        )
                        .withBody("{\"id\":1,\"username\":\"j.gurke\",\"email\":\"jochen@gurke.de\"}")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		User user = rc.getUser("j.gurke");
		assertTrue(user.getUsername().equals("j.gurke")); 
	}
	
	@Test(expected = AssertionError.class)
	public void test04_requestUser_failed() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/getUserByName")
	                    .withMethod("POST")
	                    .withBody("j.gurke")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "application/json; charset=utf-8")
                        )
                        .withBody("{\"id\":1,\"username\":\"j.gurke\",\"email\":\"jochen@gurke.de\"}")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		User user = rc.getUser("g.jurke");
		fail("unexpected success");
	}
	
	@Test
	public void test05_addUser_success() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/addUser")
	                    .withMethod("POST")
	                    .withBody("TESTUSER")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "text/plain; charset=utf-8")
                        )
                        .withBody("success")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		String returnCode = rc.addUser("TESTUSER");
		assertEquals("success", returnCode);
	}
	
	@Test
	public void test06_addUser_failed() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/addUser")
	                    .withMethod("POST")
	                    .withBody("")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "text/plain; charset=utf-8")
                        )
                        .withBody("failed")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		String returnCode = rc.addUser("");
		assertEquals("failed", returnCode);
	}
	
	@Test
	public void test07_updateUser_success() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/updateUser")
	                    .withMethod("POST")
	                    .withBody("{\"id\":0,\"username\":\"UPDATEUSER\",\"email\":\"m@a.il\"}")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "text/plain; charset=utf-8")
                        )
                        .withBody("success")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		
		String returnCode = rc.updateUser( new User("UPDATEUSER", "m@a.il") );
		assertEquals("success", returnCode);
	}
	
	@Test
	public void test08_updateUser_failed() {
		mockServer.when(
	            request()
	                    .withPath("/RestExample/rest/updateUser")
	                    .withMethod("POST")
	                    .withBody("{\"id\":0,\"username\":\"UNKNOWUSER\",\"email\":\"m@a.il\"}")
	    ).respond(
	            response()
	                    .withStatusCode(200)
	                    .withHeaders(
                                new Header("Content-Type", "text/plain; charset=utf-8")
                        )
                        .withBody("failed")
                        .withDelay(new Delay(SECONDS, 1))
	    );
		
		
		String returnCode = rc.updateUser( new User("UNKNOWUSER", "m@a.il") );
		assertEquals("failed", returnCode);
	}
	
}
