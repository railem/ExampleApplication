package com.openlimit.ExampleApplication.ExampleBackend.JPAExample;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "T_Teams")
public class Team {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY) 	
	private int teamId;
	
	private String name;
	
	@ManyToMany
	@JoinTable(
	    name="T_User_Team",
	    joinColumns=
	        @JoinColumn(name="teamId", referencedColumnName="teamId"),
	    inverseJoinColumns=
	        @JoinColumn(name="userId", referencedColumnName="userId"))
	private List<User> users;

	public Team() {
		users = new ArrayList<User>();
	}

	public Team(String name) {
		super();
		this.name = name;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public void removeUser(User user) {
		this.users.remove(user);
	}
	
}
