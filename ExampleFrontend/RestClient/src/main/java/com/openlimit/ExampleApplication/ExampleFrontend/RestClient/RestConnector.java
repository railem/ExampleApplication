package com.openlimit.ExampleApplication.ExampleFrontend.RestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.POST;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;

public class RestConnector {
	
	private String protocol, host, context;
	private int port;

	public RestConnector(String protocol, String host, int port, String context) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.context = context;
	}
	
	public Object get(String context, Class<?> expectedClass) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {		
			HttpHost target = new HttpHost(host, port, protocol);
			HttpGet getRequest = new HttpGet("/"+this.context+"/"+context);
			HttpResponse httpResponse = httpclient.execute(target, getRequest);
			HttpEntity entity = httpResponse.getEntity();
			Header[] headers = httpResponse.getAllHeaders();

			if (entity != null) {
				
				
				if(!expectedClass.equals(String.class)) {
					return jsonToObjectList(expectedClass, EntityUtils.toString(entity));
				}
				else{
					return EntityUtils.toString(entity);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return "";
	}
			
	
	public Object post( String context, String contentType, Object object, Class<?> expectedClass) {
		try {
			URL url = new URL(protocol+"://"+host+":"+port+"/"+this.context+"/"+context);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", contentType);
			
			OutputStream os = conn.getOutputStream();
				
			if(object.getClass().equals(String.class)) {
				os.write(((String) object).getBytes());
				os.flush();
			}
			else{
				Object obj = objectToJson(object);
				os.write(((String) obj).getBytes());
				os.flush();
			}


			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String completeString = "";
			String output;
			while ((output = br.readLine()) != null) {
				completeString += output;
			}
			
			conn.disconnect();
			
			return jsonToObject(expectedClass, completeString);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Object objectToJson(Object obj) {
		Gson gson = new Gson();
		
		return gson.toJson(obj);
	}

	private Object jsonToObject(Class<?> expectedClass, String completeString) {
		Gson gson = new Gson();
		Object returnObject = gson.fromJson(completeString, expectedClass);
		
		return returnObject;
	}
	
	private List<?> jsonToObjectList(Class<?> expectedClass, String completeString) {
		Gson gson = new Gson();
		
		return gson.fromJson(completeString, new ListOFJson<>(expectedClass));
//		return new ArrayList<>(Arrays.asList(gson.fromJson(completeString, expectedClass)));
	}
}
