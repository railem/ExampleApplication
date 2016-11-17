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

public class RestClient {
	
	private String serverUrl = "http://localhost:8080/RestExample/rest/";

	public String getFromRest(String context){
		 DefaultHttpClient httpclient = new DefaultHttpClient();
		    try {
		      // specify the host, protocol, and port
		      HttpHost target = new HttpHost("localhost", 8080, "http");
		      
		      // specify the get request
		      HttpGet getRequest = new HttpGet("/RestExample/rest/"+context);

		      System.out.println("executing request to " + target);

		      HttpResponse httpResponse = httpclient.execute(target, getRequest);
		      HttpEntity entity = httpResponse.getEntity();

		      System.out.println("----------------------------------------");
		      System.out.println(httpResponse.getStatusLine());
		      Header[] headers = httpResponse.getAllHeaders();
		      for (int i = 0; i < headers.length; i++) {
		        System.out.println(headers[i]);
		      }
		      System.out.println("----------------------------------------");

		      if (entity != null) {
//		        System.out.println(EntityUtils.toString(entity));
		        return EntityUtils.toString(entity);
		      }

			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
		      // When HttpClient instance is no longer needed,
		      // shut down the connection manager to ensure
		      // immediate deallocation of all system resources
		      httpclient.getConnectionManager().shutdown();
		    }
		return "null";
	}
	
	public static void main(String[] args) {
	    DefaultHttpClient httpclient = new DefaultHttpClient();
	    try {
	      // specify the host, protocol, and port
	      HttpHost target = new HttpHost("localhost", 8080, "http");
	      
	      // specify the get request
	      HttpGet getRequest = new HttpGet("/RestExample/rest/users");

	      System.out.println("executing request to " + target);

	      HttpResponse httpResponse = httpclient.execute(target, getRequest);
	      HttpEntity entity = httpResponse.getEntity();

	      System.out.println("----------------------------------------");
	      System.out.println(httpResponse.getStatusLine());
	      Header[] headers = httpResponse.getAllHeaders();
	      for (int i = 0; i < headers.length; i++) {
	        System.out.println(headers[i]);
	      }
	      System.out.println("----------------------------------------");

	      if (entity != null) {
	        System.out.println(EntityUtils.toString(entity));
	      }

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
	      // When HttpClient instance is no longer needed,
	      // shut down the connection manager to ensure
	      // immediate deallocation of all system resources
	      httpclient.getConnectionManager().shutdown();
	    }
	  }

	public void postRest(String value) {
		try {
			URL url = new URL(serverUrl+"addUser");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/plain");

			OutputStream os = conn.getOutputStream();
			os.write(value.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void updateUser(String json) {
		try {
			URL url = new URL(serverUrl+"updateUser");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
			
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public String getUserByName(String name) {
		try {
			URL url = new URL(serverUrl+"getUserByName");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/plain");
			
			OutputStream os = conn.getOutputStream();
			os.write(name.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				return output;
			}

			conn.disconnect();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "";
	}
}
