package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

public class ManagementServiceImpl {
	
	
	private EntityManager em;
	
	public ManagementServiceImpl() {
		
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public List<User> getUsernames(){
		
		String queryString = "SELECT u FROM User u";
		TypedQuery<User> query = em.createQuery(queryString, User.class);
		List<User> results = query.getResultList();

		return results;
	}

	public List<Team> getTeams(){
		
		String queryString = "SELECT t FROM Team t";
		TypedQuery<Team> query = em.createQuery(queryString, Team.class);
		List<Team> results = query.getResultList();

		return results;
	}
	
	public String addUser(String name){
		
		try {
			createUser(name, "empty@empty.empty");
			return "success";
		} catch (Exception e) {
			return "failed";
		}
	}
	
	public void createUser(String name, String email) throws RollbackException{
		User user = new User( name, email );
		
        em.persist(user);
        
	}

	User user = null;
	public User getUserByName(String name) {
		user = null;
		String queryString = "SELECT u FROM User u WHERE u.username = '"+name+"'";
		TypedQuery<User> query = em.createQuery(queryString, User.class);
		List<User> results = query.getResultList();
		results.forEach(u -> {
			user = u;
		});

		return user;
	}

	public String updateUser(User user) throws AssertionError {
		String queryString = "SELECT u FROM User u WHERE u.id = '"+user.getId()+"'";
		TypedQuery<User> query = em.createQuery(queryString, User.class);
		List<User> results = query.getResultList();
		
		if(!results.isEmpty()) 
		{
			results.forEach(u -> {
				u.setEmail(user.getEmail());
				u.setUsername(user.getUsername());
			});
			return "success";
		}
		else
		{
			return "failed";
		}
	}
	
	private User userUser = null;
	public void deleteUser(String user) throws AssertionError {

		userUser = null;
		String queryString = "SELECT u FROM User u WHERE u.username = '"+user+"'";
		TypedQuery<User> query = em.createQuery(queryString, User.class);
		List<User> results = query.getResultList();
		results.forEach(u -> {
			userUser = u;
		});
		
		em.remove(userUser);
	}
}
