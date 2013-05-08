package org.example.spring.dao;

import org.example.spring.domain.Account;

public interface AccountDao {

	/**
	 * 读取用户信息
	 * 
	 * @param username
	 * @return
	 */
	Account read(String username);

}
