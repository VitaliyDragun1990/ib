package com.revenat.iblog.domain.entity;

public class Category extends AbstractEntity<Integer>{
	private static final long serialVersionUID = 6907088223410266492L;

	private String name;
	private String url;
	private int numberOfArticles;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getNumberOfArticles() {
		return numberOfArticles;
	}
	public void setNumberOfArticles(int numberOfArticles) {
		this.numberOfArticles = numberOfArticles;
	}
	@Override
	public String toString() {
		return String.format("Category [id=%s, name=%s, url=%s, numberOfArticles=%s]",getId(), name, url, numberOfArticles);
	}
}
