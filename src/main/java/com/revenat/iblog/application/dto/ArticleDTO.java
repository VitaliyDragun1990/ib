package com.revenat.iblog.application.dto;

import java.time.LocalDateTime;

import com.revenat.iblog.application.dto.base.BaseDTO;
import com.revenat.iblog.domain.entity.Article;

public class ArticleDTO extends BaseDTO<Long, Article> {
	private String title;
	private String url;
	private String logo;
	private String description;
	private String content;
	private int categoryId;
	private LocalDateTime created;
	private long numberOfViews;
	private int numberOfComments;
	private String articleLink;
	
	
	@Override
	public void transform(Article entity) {
		super.transform(entity);
		this.articleLink = "/article/" + getId() + url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public long getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(long numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getArticleLink() {
		return articleLink;
	}

	public void setArticleLink(String articleLink) {
		this.articleLink = articleLink;
	}
}
