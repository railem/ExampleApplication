package com.openlimit.ExampleApplication.ExampleCommon.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 	
	@Column(name = "userId")
	private int id;
	
	private String username;
	private String email;

	public User() {
		
	}
	
	public User( String username, String email) {
		this.username = username;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
