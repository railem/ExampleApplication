package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.List;

import javax.ejb.Local;

@Local
public interface I_ManagementServiceSessionLocal {

	void createUser(String name, String email);

	List<User> getUsernames();

	List<Team> getTeams();

	String addUser(String name);

	User getUserByName(String name);

	String updateUser(User user);

	void deleteUser(String user);

}