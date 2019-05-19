package com.revenat.iblog.presentation.form;

public class CommentForm {
	private Long articleId;
	private String content;
	private String authToken;

	public Long getArticleId() {
		return articleId;
	}

	public String getContent() {
		return content;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
