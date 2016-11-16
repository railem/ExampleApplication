package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;

@Stateless
public class ManagementServiceSessionBean 
	implements I_ManagementServiceSessionLocal {

	//@PersistenceUnit(unitName="JPAExample")
	//private EntityManagerFactory emf;
	
	@PersistenceContext(unitName="JPAExample")
	private EntityManager em;
	
	private ManagementServiceImpl impl =null;
	
	/* (non-Javadoc)
	 * @see com.openlimit.ExampleApplication.ExampleBackend.JPAExample.I_ManagementServiceSessionLocal#createUser(java.lang.String, java.lang.String)
	 */
	@Override
	public void createUser(String name, String email){
		getBL().createUser(name, email);
	}
	
	/* (non-Javadoc)
	 * @see com.openlimit.ExampleApplication.ExampleBackend.JPAExample.I_ManagementServiceSessionLocal#getUsernames()
	 */
	@Override
	public List<User> getUsernames(){
		return getBL().getUsernames();
	}
	
	/* (non-Javadoc)
	 * @see com.openlimit.ExampleApplication.ExampleBackend.JPAExample.I_ManagementServiceSessionLocal#getTeams()
	 */
	@Override
	public List<Team> getTeams(){
		return getBL().getTeams();
	}
	
	/* (non-Javadoc)
	 * @see com.openlimit.ExampleApplication.ExampleBackend.JPAExample.I_ManagementServiceSessionLocal#addUser(java.lang.String)
	 */
	@Override
	public String addUser(String name){
		return getBL().addUser(name);
	}
	
	/* (non-Javadoc)
	 * @see com.openlimit.ExampleApplication.ExampleBackend.JPAExample.I_ManagementServiceSessionLocal#getUserByName(java.lang.String)
	 */
	@Override
	public User getUserByName(String name){
		return getBL().getUserByName(name);
	}
	
	/* (non-Javadoc)
	 * @see com.openlimit.ExampleApplication.ExampleBackend.JPAExample.I_ManagementServiceSessionLocal#updateUser(com.openlimit.ExampleApplication.ExampleBackend.JPAExample.User)
	 */
	@Override
	public String updateUser(User user){
		return getBL().updateUser(user);
	}
	
	/* (non-Javadoc)
	 * @see com.openlimit.ExampleApplication.ExampleBackend.JPAExample.I_ManagementServiceSessionLocal#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String user){
		getBL().deleteUser(user);
	}
	
	private ManagementServiceImpl getBL()
	{
		if( impl != null )
			return impl;
		
		impl = new ManagementServiceImpl();
		
//		EntityManager em = emf.createEntityManager();
		impl.setEntityManager(em);
		
		return impl;
	}
}
