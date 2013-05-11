package org.example.spring.dao.impl;

import org.springframework.stereotype.Repository;
import org.example.spring.dao.AccountDao;
import org.example.spring.domain.Account;

@Repository
public class AccountDaoImpl implements AccountDao {
	@Override
	public Account read(String username) {
		return new Account(username,"123");
	}

}
