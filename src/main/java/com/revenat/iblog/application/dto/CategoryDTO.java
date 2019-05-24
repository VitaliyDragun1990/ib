package com.revenat.iblog.application.dto;

import com.revenat.iblog.application.dto.base.BaseDTO;
import com.revenat.iblog.domain.entity.Category;

public class CategoryDTO  extends BaseDTO<Integer, Category> {
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
}
