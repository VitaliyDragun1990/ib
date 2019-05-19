package com.revenat.iblog.application.service;

import java.io.IOException;

import com.revenat.iblog.application.domain.entity.Account;
import com.revenat.iblog.application.domain.model.SocialAccount;
import com.revenat.iblog.application.infra.exception.PersistenceException;
import com.revenat.iblog.application.infra.exception.flow.FlowException;
import com.revenat.iblog.persistence.repository.AccountRepository;

public class AuthenticationService {
	private final SocialService socialService;
	private final AvatarService avatarService;
	private final AccountRepository accountRepo;
	
	public AuthenticationService(SocialService socialService, AvatarService avatarService, AccountRepository accountRepo) {
		this.socialService = socialService;
		this.avatarService = avatarService;
		this.accountRepo = accountRepo;
	}

	public Account authenticate(String authToken) {
		SocialAccount socialAccount = socialService.getSocialAccount(authToken);
		Account account = accountRepo.getByEmail(socialAccount.getEmail());
		if (account == null) {
			account = createNewAccount(socialAccount);
		}
		return account;
	}

	private Account createNewAccount(SocialAccount socialAccount) {
		String avatarPath = null;
		try {
			// download avatar from social service and store it in server's filesystem
			avatarPath = avatarService.downloadAvatar(socialAccount.getAvatar());
			// create new account
			Account account = new Account();
			account.setEmail(socialAccount.getEmail());
			account.setName(socialAccount.getName());
			account.setAvatar(avatarPath);
			// save new account
			account = accountRepo.save(account);
			return account;
		} catch (PersistenceException | IOException e) {
			avatarService.deleteAvatarIfExists(avatarPath);
			throw new FlowException("Error while creating new user's account", e);
		}
	}
}
