package org.example.spring.service;

public interface AccountService {

	/**
	 * 验证用户身份
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	boolean verify(String username, String password);

}
