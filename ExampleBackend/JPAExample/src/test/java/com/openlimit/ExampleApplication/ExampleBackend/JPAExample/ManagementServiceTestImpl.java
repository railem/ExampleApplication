package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.List;

import javax.persistence.EntityManager;

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
		em.close();
		
		return result;
	}
	
	@Override
	public List<User> getUsernames() {
		em.getTransaction().begin();
		List<User> result = super.getUsernames();
		em.getTransaction().commit();
		em.close();
		
		return result;
	}
	
	@Override
	public List<Team> getTeams() {
		em.getTransaction().begin();
		List<Team> result = super.getTeams();
		em.getTransaction().commit();
		em.close();
		
		return result;
	}
	
	@Override
	public User getUserByName(String name) {
		em.getTransaction().begin();
		User result = super.getUserByName(name);
		em.close();
		
		return result;
	}
	
	@Override
	public String updateUser(User user) {
		em.getTransaction().begin();
		String result = super.updateUser(user);
		em.getTransaction().commit();
		em.close();
		
		return result;
	}

}
