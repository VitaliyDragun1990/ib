package com.revenat.iblog.application.service.impl;

import java.io.IOException;

import com.revenat.iblog.application.service.AuthenticationService;
import com.revenat.iblog.domain.entity.Account;
import com.revenat.iblog.infrastructure.exception.AuthenticationException;
import com.revenat.iblog.infrastructure.exception.PersistenceException;
import com.revenat.iblog.infrastructure.exception.flow.FlowException;
import com.revenat.iblog.infrastructure.repository.AccountRepository;
import com.revenat.iblog.infrastructure.service.AvatarService;
import com.revenat.iblog.infrastructure.service.SocialAccount;
import com.revenat.iblog.infrastructure.service.SocialService;

class SocialAccountAuthenticationService implements AuthenticationService {
	private final SocialService socialService;
	private final AvatarService avatarService;
	private final AccountRepository accountRepo;
	
	public SocialAccountAuthenticationService(SocialService socialService, AvatarService avatarService, AccountRepository accountRepo) {
		this.socialService = socialService;
		this.avatarService = avatarService;
		this.accountRepo = accountRepo;
	}

	@Override
	public long authenticate(String authToken) throws AuthenticationException {
		SocialAccount socialAccount = socialService.getSocialAccount(authToken);
		Account account = accountRepo.getByEmail(socialAccount.getEmail());
		if (account == null) {
			account = createNewAccount(socialAccount);
		}
		return account.getId();
	}

	private Account createNewAccount(SocialAccount socialAccount) {
		String avatarPath = null;
		try {
			// download avatar from social service and store it in server's filesystem
			avatarPath = avatarService.downloadAvatar(socialAccount.getAvatar());
			Account account = createAccount(socialAccount, avatarPath);
			account = accountRepo.save(account);
			return account;
		} catch (PersistenceException | IOException e) {
			avatarService.deleteAvatarIfExists(avatarPath);
			throw new FlowException("Error while creating new user's account", e);
		}
	}

	private Account createAccount(SocialAccount socialAccount, String avatarPath) {
		Account account = new Account();
		account.setEmail(socialAccount.getEmail());
		account.setName(socialAccount.getName());
		account.setAvatar(avatarPath);
		return account;
	}
}
