package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ManagementServiceImpl {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public ManagementServiceImpl() {
		initJPA();
		
//		createTestData();
//		testTestData();
	}
	
	private String usernamesString;
	public String getUsernames(){
		
		usernamesString = "";
		
		em.getTransaction().begin();
		
		String queryString = "SELECT u FROM User u";
		TypedQuery<User> query = em.createQuery(queryString, User.class);
		List<User> results = query.getResultList();
		results.forEach(u -> {
			usernamesString = usernamesString + u.getUsername() + "; ";
		});
			
        em.getTransaction().commit();
        em.close();
		
		
		return usernamesString;
	}
	
	private String teamsString;
	public String getTeams(){
		
		teamsString = "";
		
		em.getTransaction().begin();
		
		String queryString = "SELECT t FROM Team t";
		TypedQuery<Team> query = em.createQuery(queryString, Team.class);
		List<Team> results = query.getResultList();
		results.forEach(t -> {
			teamsString = teamsString + t.getName() + "; ";
		});
			
        em.getTransaction().commit();
        em.close();
		
		
		return teamsString;
	}
	
	public String addUser(String name){
		
		try {
			createUser(name, "empty@empty.empty");
			return "success";
		} catch (Exception e) {
			return "failed";
		}
	}
	
	public void createUser(String name, String email){
		User user = new User( name, email );
		
		em.getTransaction().begin();
		
        em.persist(user);
        
        em.getTransaction().commit();
        em.close();
	}

	private void testTestData() {
		em.getTransaction().begin();
		
		String queryString = "SELECT u FROM User u WHERE u.username = 'r.iven'";
		TypedQuery<User> query = em.createQuery(queryString, User.class);
		List<User> results = query.getResultList();
		results.forEach(u -> {
			System.out.println(u.getUsername());
			u.setEmail("iven@regina.de");
		});
			
        em.getTransaction().commit();
        em.close();
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
        
		em.getTransaction().begin();
		
        em.persist(testUser1);
        em.persist(testUser2);
        em.persist(testUser3);
        em.persist(testUser4);
        em.persist(testUser5);
        
        em.persist(testTeam1);
        em.persist(testTeam2);
        
        em.getTransaction().commit();

        em.close();
	}

	public static void main(String[] args) {
		
		ManagementServiceImpl main = new ManagementServiceImpl();
	}

	private void initJPA() {
		emf = Persistence.createEntityManagerFactory("JPAExample");
		em = emf.createEntityManager();
		
	}
}
