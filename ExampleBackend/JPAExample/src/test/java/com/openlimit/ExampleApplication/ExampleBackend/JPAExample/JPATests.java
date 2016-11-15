package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JPATests {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("JPAExample");
		em = emf.createEntityManager();
	}
	
	@After
	public void close() {
		em.close();
		emf.close();
	}
	
	@Test
	public void test01_addUser_success() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		String result = impl.addUser("TEST");
		
		assertNotNull(result);
		assertTrue(result.equals("success"));
	}
	
	@Test(expected = RollbackException.class)
	public void test02_addUser_failed() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		String result = impl.addUser(null);
		
		fail("unexpected success");
	}
	
	@Test
	public void test03_getUsernames_success() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		List<User> users = impl.getUsernames();
		
		assertNotNull(users);
		assertTrue(!users.isEmpty());
	}

	@Test
	public void test04_getTeams_success() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		List<Team> teams = impl.getTeams();
		
		assertNotNull(teams);
		assertTrue(!teams.isEmpty());
	}
	
	@Test
	public void test05_getUserByName_success() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		User user = impl.getUserByName("TEST");
		
		assertNotNull(user);
		assertEquals("TEST", user.getUsername());
	}
	
	@Test
	public void test06_getUserByName_failed() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		User user = impl.getUserByName("34f2g5zAWfr");
		
		assertNull(user);
	}
	
	@Test
	public void test07_updateUser_success() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		User user = impl.getUserByName("TEST");
		user.setUsername("TEST_UPDATED");
		
		String result = impl.updateUser(user);
		
		assertNotNull(result);
		assertTrue(result.equals("success"));
	}
	
	@Test(expected = AssertionError.class)
	public void test08_updateUser_failed() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		User user = new User();
		user.setUsername("awda124V35");
		user.setEmail("awdad@gg.ge");
		
		String result = impl.updateUser(user);
		
		fail("unexpected success");
	}
	
	@Test
	public void test09_deleteUser_success() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		User user = impl.getUserByName("TEST_UPDATED");
		
		impl.deleteUser(user);
		
		User userAfterDelete = impl.getUserByName("TEST_UPDATED");
		assertNull(userAfterDelete);
	}
	
	@Test(expected = AssertionError.class)
	public void test10_deleteUser_failed() {
		ManagementServiceTestImpl impl = new ManagementServiceTestImpl();
		impl.setEntityManager(em);
		
		User user = new User();
		user.setUsername("awda124V35");
		user.setEmail("awdad@gg.ge");
		impl.deleteUser(user);
		
		fail("unexpected success");
	}
}
