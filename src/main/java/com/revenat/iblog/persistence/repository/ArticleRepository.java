package com.revenat.iblog.persistence.repository;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.search.criteria.ArticleCriteria;

public interface ArticleRepository {
	
	Article getById(long id);

	List<Article> getAll(int offset, int limit);
	
	List<Article> getByCategory(int categoryId, int offset, int limit);
	
	List<Article> getByCriteria(ArticleCriteria searchCriteria, int offset, int limit);
	
	void update(Article article);
	
	long getTotalCount();
	
	long getCountByCategory(int categoryId);

	long getCountByCriteria(ArticleCriteria searchCriteria);
}
