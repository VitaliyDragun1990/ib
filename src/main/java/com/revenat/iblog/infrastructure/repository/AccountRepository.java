package com.revenat.iblog.infrastructure.repository;

import com.revenat.iblog.domain.entity.Account;

public interface AccountRepository {

	Account getById(long id);
	
	Account getByEmail(String email);
	
	Account save(Account account);
}
