package com.openlimit.ExampleApplication.ExampleFrontend.RestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.openlimit.ExampleApplication.ExampleCommon.common.Team;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;


public class RestClient {
	
	private RestConnector rc;
	
	public RestClient(String protocol, String host, int port, String context) {
		rc = new RestConnector(protocol, host, port, context);
	}

	public List<User> getUserAsList(){

		return (List<User>) rc.get("users", User.class);
	}
	
	public String getUserListAsString(){

		return (String) rc.get("users", User.class);
	}
	
	public List<Team> getTeamList(){

		return (List<Team>) rc.get("teams", Team.class);
	}

	public String addUser(String name) {
		return (String) rc.post("addUser", "text/plain", name, String.class);
	}
	
	public User getUser(String name) {
		
		return (User) rc.post("getUserByName", "text/plain", name, User.class);
	}

	public String updateUser(User user) {
		return (String) rc.post("updateUser", "application/json", user, String.class);
	}
	
	public boolean destinationReachable() {
		return rc.destinationReachable();
	}
}
