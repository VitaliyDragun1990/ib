package com.revenat.iblog.application.dto;

import java.time.LocalDateTime;

import com.revenat.iblog.application.dto.base.BaseDTO;
import com.revenat.iblog.domain.entity.Account;

public class AccountDTO extends BaseDTO<Long, Account> {
	private String email;
	private String name;
	private String avatar;
	private LocalDateTime created;

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
