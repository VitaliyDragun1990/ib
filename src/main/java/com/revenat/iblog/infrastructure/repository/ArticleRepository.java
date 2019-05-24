package com.revenat.iblog.infrastructure.repository;

import java.util.List;

import com.revenat.iblog.domain.entity.Article;
import com.revenat.iblog.domain.search.criteria.ArticleCriteria;

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
