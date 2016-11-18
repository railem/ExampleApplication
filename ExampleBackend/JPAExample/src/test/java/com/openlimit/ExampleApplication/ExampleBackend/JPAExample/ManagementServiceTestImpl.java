package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.List;

import javax.persistence.EntityManager;

import com.openlimit.ExampleApplication.ExampleCommon.common.Team;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;

public class ManagementServiceTestImpl extends ManagementServiceImpl {

	private EntityManager em;

	@Override
	public void setEntityManager(EntityManager em) {
		this.em = em;
		super.setEntityManager(em);
	}

	@Override
	public String addUser(String name) {
		em.getTransaction().begin();
		String result = super.addUser(name);
		em.getTransaction().commit();
		
		return result;
	}
	
	@Override
	public List<User> getUsernames() {
		em.getTransaction().begin();
		List<User> result = super.getUsernames();
		em.getTransaction().commit();
		
		return result;
	}
	
	@Override
	public List<Team> getTeams() {
		em.getTransaction().begin();
		List<Team> result = super.getTeams();
		em.getTransaction().commit();
		
		return result;
	}
	
	@Override
	public User getUserByName(String name) {
		em.getTransaction().begin();
		User result = super.getUserByName(name);
		
		return result;
	}
	
	@Override
	public String updateUser(User user) {
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		String result = super.updateUser(user);
		em.getTransaction().commit();
		
		return result;
	}
	
	@Override
	public void deleteUser(String user) {
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		super.deleteUser(user);
		em.getTransaction().commit();

	}

}
