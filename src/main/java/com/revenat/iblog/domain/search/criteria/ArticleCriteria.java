package com.revenat.iblog.domain.search.criteria;

import com.revenat.iblog.domain.entity.Article;

/**
 * This value object represents criteria object to search for {@link Article} with.
 * 
 * @author Vitaly Dragun
 *
 */
public class ArticleCriteria {
	private final String query;
	private final Integer categoryId;
	
	public ArticleCriteria(String query, Integer categoryId) {
		this.query = query;
		this.categoryId = categoryId;
	}
	
	public boolean isCategorySpecified() {
		return categoryId != null;
	}

	public String getQuery() {
		return query;
	}

	public Integer getCategoryId() {
		return categoryId;
	}
}
