package com.revenat.iblog.application.domain.entity;

import java.time.LocalDateTime;

/**
 * @author Vitaly Dragun
 *
 */
public class Comment extends AbstractEntity<Long> {
	private static final long serialVersionUID = -3911961143427127092L;

	private Account account;
	private long articleId;
	private String content;
	private LocalDateTime created;
	
	public Comment() {
		created = LocalDateTime.now();
	}
	
	public Comment(long articleId, Account account, String content, LocalDateTime created) {
		this.articleId = articleId;
		this.account = account;
		this.content = content;
		this.created = created;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
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
