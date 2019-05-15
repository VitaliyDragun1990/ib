package com.revenat.iblog.persistence.repository;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Article;

public interface ArticleRepository {

	List<Article> getAll(int offset, int limit);
	
	List<Article> getByCategory(int categoryId, int offset, int limit);
	
	long getTotalCount();
	
	long getCountByCategory(int categoryId);
}
