package com.revenat.iblog.application.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Article extends AbstractEntity<Long> {
	private static final long serialVersionUID = 2150459541573382060L;

	private String title;
	private String url;
	private String logo;
	private String description;
	private String content;
	private Category category;
	private LocalDateTime created;
	private long numberOfViews;
	private List<Comment> comments;
	
	public Article() {
		created = LocalDateTime.now();
		comments = new ArrayList<>();
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
	public List<Comment> getComments() {
		return Collections.unmodifiableList(comments);
	}
	public void setComments(List<Comment> comments) {
		this.comments = new ArrayList<>();
		this.comments.addAll(comments);
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

	@Override
	public String toString() {
		return String.format(
				"Article [id=%s, title=%s, url=%s, logo=%s, description=%s, content=%s, category=%s, created=%s, numberOfViews=%s, comments=%s]",
				getId(), title, url, logo, description, content, category, created, numberOfViews, comments);
	}
	
	
}
