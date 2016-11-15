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

	private void testTestData() {
		
		String queryString = "SELECT u FROM User u WHERE u.username = 'r.iven'";
		TypedQuery<User> query = em.createQuery(queryString, User.class);
		List<User> results = query.getResultList();
		results.forEach(u -> {
			System.out.println(u.getUsername());
			u.setEmail("iven@regina.de");
		});
	}

	private void createTestData() {
		
		User testUser1 = new User( "j.gurke", "jochen@gurke.de" );
		User testUser2 = new User( "r.iven", "iven@regina.com" );
		User testUser3 = new User( "k.arl", "karl@arl.de" );
		User testUser4 = new User( "j.spargel", "juergen@spargel.de" );
		User testUser5 = new User( "j.lauch", "jacob@lauch.de" );
		
		Team testTeam1 = new Team();
		testTeam1.setName("WurstTeam");
		testTeam1.addUser(testUser1);
		testTeam1.addUser(testUser2);
		
		Team testTeam2 = new Team();
		testTeam2.setName("TeamTratsch");
		testTeam2.addUser(testUser3);
		testTeam2.addUser(testUser4);
		testTeam2.addUser(testUser5);
        
        em.persist(testUser1);
        em.persist(testUser2);
        em.persist(testUser3);
        em.persist(testUser4);
        em.persist(testUser5);
        
        em.persist(testTeam1);
        em.persist(testTeam2);
	}

	public static void main(String[] args) {
		
		ManagementServiceImpl main = new ManagementServiceImpl();
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
