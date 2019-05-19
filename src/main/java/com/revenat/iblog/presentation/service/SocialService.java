package com.revenat.iblog.presentation.service;

import com.revenat.iblog.presentation.infra.exception.AuthenticationException;
import com.revenat.iblog.presentation.model.SocialAccount;

/**
 * This interface represents component responsible for getting access to
 * user's account from some sort of social network.
 * 
 * @author Vitaly Dragun
 *
 */
public interface SocialService {
	
	/**
	 * Gets {@link SocialAccount} of authenticated social network user using
	 * provided {@code authToken} parameter.
	 * 
	 * @param authToken authentication token that will be used to get user's account
	 *                  in social network.
	 * @return {@link SocialAccount} instance that represents user's social network
	 *         account
	 * @throws AuthenticationException if error occurs while getting information
	 *                                 about client's social network account
	 */
	SocialAccount getSocialAccount(String authToken);
}
