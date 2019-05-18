package com.revenat.iblog.application.service;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.application.domain.model.Items;
import com.revenat.iblog.application.domain.search.criteria.ArticleCriteria;
import com.revenat.iblog.application.infra.exception.ResourceNotFoundException;
import com.revenat.iblog.application.infra.util.Checks;
import com.revenat.iblog.persistence.repository.ArticleRepository;
import com.revenat.iblog.persistence.repository.CategoryRepository;

/**
 * This component contains logic related to {@link Article} entity.
 * 
 * @author Vitaly Dragun
 *
 */
public class ArticleService {
	private final ArticleRepository articleRepo;
	private final CategoryRepository categoryRepo;

	public ArticleService(ArticleRepository articleRepo, CategoryRepository categoryRepo) {
		this.articleRepo = articleRepo;
		this.categoryRepo = categoryRepo;
	}

	/**
	 * Returns {@link Items} object with {@link Article} instances for given page.
	 * 
	 * @param pageNumber page for which articles are to be returned.
	 * @param pageSize   max number of articles per page
	 * @return {@link Items} with articles for given page or empty one if no
	 *         articles were found.
	 */
	public Items<Article> listArticles(int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);

		List<Article> articles = articleRepo.getAll(calculateOffset(pageNumber, pageSize), pageSize);
		long totalArticles = articleRepo.getTotalCount();

		return new Items<>(articles, totalArticles);
	}

	/**
	 * Returns {@link Items} object with {@link Article} instances wich belong to
	 * particular {@link Category} denoted by {@code categoryUrl} parameter for
	 * given page.
	 * 
	 * @param categoryUrl url of the category for which articles are to be returned
	 * @param pageNumber  page for which articles are to returned.
	 * @param pageSize    max number of articles per page
	 * @return {@link Items} with articles belonging to particular category for
	 *         given page or empty one if no articles were found.
	 * @throws ResourceNotFoundException if category denoted by specified
	 *                                   {@code categoryUrl} parameter does not
	 *                                   exist.
	 */
	public Items<Article> listArticlesByCategory(String categoryUrl, int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);

		Category category = categoryRepo.getByUrl(categoryUrl);
		Checks.checkResource(category, "There is no category for such url: %s", categoryUrl);
		List<Article> articles = articleRepo.getByCategory(category.getId(), calculateOffset(pageNumber, pageSize),
				pageSize);
		long countByCategory = articleRepo.getCountByCategory(category.getId());

		return new Items<>(articles, countByCategory);
	}

	/**
	 * Returns {@link Items} object with {@link Article} instances wich belong to
	 * particular {@link Category} denoted by {@code categoryUrl} parameter (if any)
	 * and at the same time satisfy specified {@code searchQuery} parameter for
	 * given page.
	 * 
	 * @param searchQuery search query articles should satisfy
	 * @param categoryUrl url of the category for which articles are to be returned,
	 *                    can be {@code null} if no category was specified.
	 * @param pageNumber  page for which articles are to returned.
	 * @param pageSize    max number of articles per page
	 * @return {@link Items} with articles belonging to particular category for
	 *         given page or empty one if no articles were found.
	 * @throws ResourceNotFoundException if {@code categoryUrl} is not {@code null}
	 *                                   and category denoted by it does not exist.
	 */
	public Items<Article> listArticlesBySearchQuery(String searchQuery, String categoryUrl, int pageNumber,
			int pageSize) {
		validate(pageNumber, pageSize);

		ArticleCriteria searchCriteria = null;
		if (categoryUrl != null) {
			Category category = categoryRepo.getByUrl(categoryUrl);
			Checks.checkResource(category, "There is no category for such url: %s", categoryUrl);
			searchCriteria = new ArticleCriteria(searchQuery, category.getId());
		} else {
			searchCriteria = new ArticleCriteria(searchQuery, null);
		}
		List<Article> articles = articleRepo.getByCriteria(searchCriteria, calculateOffset(pageNumber, pageSize),
				pageSize);
		long countByCriteria = articleRepo.getCountByCriteria(searchCriteria);

		return new Items<>(articles, countByCriteria);
	}

	/**
	 * Finds {@link Article} using specified {@code articleId} parameter.
	 * 
	 * @param articleId if of the article to find.
	 * @return article with such id.
	 * @throws ResourceNotFoundException if no article with specified id was found.
	 */
	public Article findArticle(long articleId) {
		Article article = articleRepo.getById(articleId);
		Checks.checkResource(article, "Article with id: %d not found", articleId);
		return article;
	}

	/**
	 * Increments {@link Article#getNumberOfViews()} attribute by {@code 1} and
	 * persist such change to the datastore.
	 */
	public void incrementArticleViewCount(Article article) {
		article.setNumberOfViews(article.getNumberOfViews() + 1);
		articleRepo.update(article);
	}

	private int calculateOffset(int pageNumber, int pageSize) {
		return (pageNumber - 1) * pageSize;
	}

	private static void validate(int pageNumber, int pageSize) {
		Checks.checkParam(pageNumber >= 1, "page number can not be less that 1: %d", pageNumber);
		Checks.checkParam(pageSize >= 1, "page size can not be less that 1: %d", pageSize);
	}
}
