package com.revenat.iblog.application.service;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.application.domain.model.Items;
import com.revenat.iblog.application.domain.search.criteria.ArticleCriteria;
import com.revenat.iblog.application.infra.util.Checks;
import com.revenat.iblog.persistence.repository.ArticleRepository;
import com.revenat.iblog.persistence.repository.CategoryRepository;

public class ArticleService {
	private final ArticleRepository articleRepo;
	private final CategoryRepository categoryRepo;
	
	public ArticleService(ArticleRepository articleRepo, CategoryRepository categoryRepo) {
		this.articleRepo = articleRepo;
		this.categoryRepo = categoryRepo;
	}

	public Items<Article> listArticles(int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);
		
		List<Article> articles = articleRepo.getAll(calculateOffset(pageNumber, pageSize), pageSize);
		long totalArticles = articleRepo.getTotalCount();
		
		return new Items<>(articles, totalArticles);
	}
	
	public Items<Article> listArticlesByCategory(String categoryUrl, int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);
		
		Category category = categoryRepo.getByUrl(categoryUrl);
		Checks.checkParam(category != null, "There is no category with such url: %s", categoryUrl);
		List<Article> articles = articleRepo.getByCategory(category.getId(), calculateOffset(pageNumber, pageSize), pageSize);
		long countByCategory = articleRepo.getCountByCategory(category.getId());
		
		return new Items<>(articles, countByCategory);
	}
	
	public Items<Article> listArticlesBySearchQuery(String searchQuery, String categoryUrl, int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);
		
		ArticleCriteria searchCriteria = null;
		if (categoryUrl != null) {
			Category category = categoryRepo.getByUrl(categoryUrl);
			Checks.checkParam(category != null, "There is no category with such url: %s", categoryUrl);
			searchCriteria = new ArticleCriteria(searchQuery, category.getId());
		} else {
			searchCriteria = new ArticleCriteria(searchQuery, null);
		}
		List<Article> articles = articleRepo.getByCriteria(searchCriteria, calculateOffset(pageNumber, pageSize), pageSize);
		long countByCriteria = articleRepo.getCountByCriteria(searchCriteria);
		
		return new Items<>(articles, countByCriteria);
	}
	
	private int calculateOffset(int pageNumber, int pageSize) {
		return (pageNumber - 1) * pageSize;
	}
	
	private static void validate(int pageNumber, int pageSize) {
		Checks.checkParam(pageNumber >= 1, "page number can not be less that 1: %d", pageNumber);
		Checks.checkParam(pageSize >= 1, "page size can not be less that 1: %d", pageSize);
	}
}
