package com.revenat.iblog.presentation.form;

import org.apache.commons.lang3.StringUtils;

import com.revenat.iblog.presentation.infra.exception.InputValidationException;

public class CommentForm extends AbstractForm {
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
	
	@Override
	public void validate() throws InputValidationException {
		if (articleId == null) {
			throw new InputValidationException("articleId is required");
		}
		if (StringUtils.isBlank(content)) {
			throw new InputValidationException("content is required");
		}
		if (StringUtils.isBlank(authToken)) {
			throw new InputValidationException("authToken is required");
		}
	}
}
