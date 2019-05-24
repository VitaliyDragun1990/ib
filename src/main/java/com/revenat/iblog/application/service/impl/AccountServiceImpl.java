package com.revenat.iblog.application.service.impl;

import com.revenat.iblog.application.dto.AccountDTO;
import com.revenat.iblog.application.service.AccountService;
import com.revenat.iblog.application.transform.Transformer;
import com.revenat.iblog.domain.entity.Account;
import com.revenat.iblog.infrastructure.repository.AccountRepository;
import com.revenat.iblog.infrastructure.util.Checks;

class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepo;
	private final Transformer transformer;

	public AccountServiceImpl(AccountRepository accountRepo, Transformer transformer) {
		this.accountRepo = accountRepo;
		this.transformer = transformer;
	}

	@Override
	public AccountDTO getById(long id) {
		Account account = accountRepo.getById(id);
		Checks.checkResource(account, "Account with id=%d not found", id);
		return transformer.transform(account, AccountDTO.class);
	}
}
