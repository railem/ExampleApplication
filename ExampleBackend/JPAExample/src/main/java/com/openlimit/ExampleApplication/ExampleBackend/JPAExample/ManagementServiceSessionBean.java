package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import javax.ejb.Stateless;

@Stateless
public class ManagementServiceSessionBean {

	private ManagementServiceImpl impl = new ManagementServiceImpl();
	
	public void createUser(String name, String email){
		impl.createUser(name, email);
	}
	
	public String getUsernames(){
		return impl.getUsernames();
	}
	
	public String getTeams(){
		return impl.getTeams();
	}
	
	public String addUser(String name){
		return impl.addUser(name);
	}
	
	public String getUserByName(String name){
		return impl.getUserByName(name);
	}
	
	public String updateUser(String json){
		return impl.updateUser(json);
	}
}
