package com.revenat.iblog.presentation.form;

public class CommentForm {
	private final Long articleId;
	private final String content;
	private final String authToken;

	public CommentForm(Long articleId, String content, String authToken) {
		this.articleId = articleId;
		this.content = content;
		this.authToken = authToken;
	}

	public Long getArticleId() {
		return articleId;
	}

	public String getContent() {
		return content;
	}

	public String getAuthToken() {
		return authToken;
	}
}
