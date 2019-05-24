package com.revenat.iblog.domain.entity;

import java.time.LocalDateTime;

/**
 * @author Vitaly Dragun
 *
 */
public class Comment extends AbstractEntity<Long> {
	private static final long serialVersionUID = -3911961143427127092L;

	private long accountId;
	private long articleId;
	private String content;
	private LocalDateTime created;
	
	public Comment() {
		created = LocalDateTime.now();
	}
	
	public Comment(long articleId, long accountId, String content, LocalDateTime created) {
		this.articleId = articleId;
		this.accountId = accountId;
		this.content = content;
		this.created = created;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
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
