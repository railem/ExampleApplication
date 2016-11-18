package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.List;

import javax.ejb.Local;

import com.openlimit.ExampleApplication.ExampleCommon.common.Team;
import com.openlimit.ExampleApplication.ExampleCommon.common.User;

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