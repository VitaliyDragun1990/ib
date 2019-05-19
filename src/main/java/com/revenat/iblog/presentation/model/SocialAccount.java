package com.revenat.iblog.presentation.model;

import com.revenat.iblog.application.domain.model.AbstractModel;

/**
 * This component represents abstraction over user's account from some kind of social network.
 * 
 * @author Vitaly Dragun
 *
 */
public class SocialAccount extends AbstractModel {
	private final String email;
	private final String name;
	private final String avatar;

	public SocialAccount(String email, String name, String avatar) {
		this.email = email;
		this.name = name;
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}
}
