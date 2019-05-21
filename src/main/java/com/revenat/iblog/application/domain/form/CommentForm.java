package com.revenat.iblog.application.domain.form;

import org.apache.commons.lang3.StringUtils;

import com.revenat.iblog.application.infra.exception.flow.InvalidParameterException;

/**
 * Component that contains information about comment to be created.
 * 
 * @author Vitaly Dragun
 *
 */
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
	public void validate() throws InvalidParameterException {
		if (articleId == null) {
			throw new InvalidParameterException("articleId is required");
		}
		if (StringUtils.isBlank(content)) {
			throw new InvalidParameterException("content is required");
		}
		if (StringUtils.isBlank(authToken)) {
			throw new InvalidParameterException("authToken is required");
		}
	}
}
