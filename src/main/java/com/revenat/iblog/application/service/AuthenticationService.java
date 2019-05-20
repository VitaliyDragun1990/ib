package com.revenat.iblog.application.service;

import com.revenat.iblog.application.domain.entity.Account;

public interface AuthenticationService {

	Account authenticate(String authToken);

}