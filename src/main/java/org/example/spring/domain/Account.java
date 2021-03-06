package org.example.spring.domain;

import java.io.Serializable;


public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -533698031946372178L;

	private String username="admin";
	private String password="123";

	/**
	 * @param username
	 * @param password
	 */
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
