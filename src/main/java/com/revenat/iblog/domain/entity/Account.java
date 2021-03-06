package com.revenat.iblog.domain.entity;

import java.time.LocalDateTime;

public class Account extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1768457871166954362L;

	private String email;
	private String name;
	private String avatar;
	private LocalDateTime created;

	public Account() {
		created = LocalDateTime.now();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
}
