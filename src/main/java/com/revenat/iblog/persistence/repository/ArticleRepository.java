package com.revenat.iblog.persistence.repository;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.search.criteria.ArticleCriteria;

public interface ArticleRepository {

	List<Article> getAll(int offset, int limit);
	
	List<Article> getByCategory(int categoryId, int offset, int limit);
	
	long getTotalCount();
	
	long getCountByCategory(int categoryId);

	List<Article> getByCriteria(ArticleCriteria searchCriteria, int offset, int limit);

	long getCountByCriteria(ArticleCriteria searchCriteria);
}
