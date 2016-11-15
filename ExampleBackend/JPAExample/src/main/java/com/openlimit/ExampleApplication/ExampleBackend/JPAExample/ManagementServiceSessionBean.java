package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@Stateless
public class ManagementServiceSessionBean {

	@PersistenceUnit(unitName="JPAExample")
	private EntityManager em;
	
	private ManagementServiceImpl impl =null;
	
	public void createUser(String name, String email){
		getBL().createUser(name, email);
	}
	
	public List<User> getUsernames(){
		return getBL().getUsernames();
	}
	
	public List<Team> getTeams(){
		return getBL().getTeams();
	}
	
	public String addUser(String name){
		return getBL().addUser(name);
	}
	
	public User getUserByName(String name){
		return getBL().getUserByName(name);
	}
	
	public String updateUser(User user){
		return getBL().updateUser(user);
	}
	
	public void deleteUser(User user){
		getBL().deleteUser(user);
	}
	
	private ManagementServiceImpl getBL()
	{
		if( impl != null )
			return impl;
		
		impl = new ManagementServiceImpl();
		impl.setEntityManager(em);
		
		return impl;
	}
}
