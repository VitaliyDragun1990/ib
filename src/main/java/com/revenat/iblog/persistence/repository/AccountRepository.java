package com.revenat.iblog.persistence.repository;

import com.revenat.iblog.application.domain.entity.Account;

public interface AccountRepository {

	Account getById(long id);
}
