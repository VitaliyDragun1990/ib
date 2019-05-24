package com.revenat.iblog.application.dto;

import java.time.LocalDateTime;

import com.revenat.iblog.application.dto.base.BaseDTO;
import com.revenat.iblog.domain.entity.Comment;
import com.revenat.iblog.infrastructure.annotation.Ignore;

public class CommentDTO extends BaseDTO<Long, Comment> {
	@Ignore
	private AccountDTO account;
	private long articleId;
	private String content;
	private LocalDateTime created;
	
	public AccountDTO getAccount() {
		return account;
	}
	public void setAccount(AccountDTO account) {
		this.account = account;
	}
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
}
