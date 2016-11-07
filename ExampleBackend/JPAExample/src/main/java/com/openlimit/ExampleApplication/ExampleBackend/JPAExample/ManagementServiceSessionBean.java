package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import javax.ejb.Stateless;

@Stateless
public class ManagementServiceSessionBean {

	private ManagementServiceImpl impl = new ManagementServiceImpl();
	
	public void createUser(String name, String email){
		impl.createUser(name, email);
	}
}
