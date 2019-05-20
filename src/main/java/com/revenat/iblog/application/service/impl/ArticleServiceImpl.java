package com.revenat.iblog.application.service.impl;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Account;
import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.application.domain.entity.Comment;
import com.revenat.iblog.application.domain.model.Items;
import com.revenat.iblog.application.domain.search.criteria.ArticleCriteria;
import com.revenat.iblog.application.infra.util.Checks;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.persistence.repository.ArticleRepository;
import com.revenat.iblog.persistence.repository.CategoryRepository;
import com.revenat.iblog.persistence.repository.CommentRepository;

/**
 * This component contains logic related to {@link Article} entity.
 * 
 * @author Vitaly Dragun
 *
 */
class ArticleServiceImpl implements ArticleService {
	private final ArticleRepository articleRepo;
	private final CategoryRepository categoryRepo;
	private final CommentRepository commentRepo;

	public ArticleServiceImpl(ArticleRepository articleRepo, CategoryRepository categoryRepo,
			CommentRepository commentRepo) {
		this.articleRepo = articleRepo;
		this.categoryRepo = categoryRepo;
		this.commentRepo = commentRepo;
	}

	@Override
	public Items<Article> listArticles(int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);

		List<Article> articles = articleRepo.getAll(calculateOffset(pageNumber, pageSize), pageSize);
		long totalArticles = articleRepo.getTotalCount();

		return new Items<>(articles, totalArticles);
	}

	@Override
	public Items<Article> listArticlesByCategory(String categoryUrl, int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);

		Category category = categoryRepo.getByUrl(categoryUrl);
		Checks.checkResource(category, "There is no category for such url: %s", categoryUrl);
		List<Article> articles = articleRepo.getByCategory(category.getId(), calculateOffset(pageNumber, pageSize),
				pageSize);
		long countByCategory = articleRepo.getCountByCategory(category.getId());

		return new Items<>(articles, countByCategory);
	}

	@Override
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

	@Override
	public Article findArticle(long articleId) {
		Article article = articleRepo.getById(articleId);
		Checks.checkResource(article, "Article with id: %d not found", articleId);
		return article;
	}

	@Override
	public void incrementArticleViewCount(Article article) {
		article.setNumberOfViews(article.getNumberOfViews() + 1);
		articleRepo.update(article);
	}

	@Override
	public List<Comment> loadComments(long articleId, int offset, int pageSize) {
		Checks.checkParam(offset >= 0, "offset can not be less that 0: %d", pageSize);
		Checks.checkParam(pageSize >= 1, "page size can not be less that 1: %d", pageSize);
		return commentRepo.getByArticle(articleId, offset, pageSize);
	}

	@Override
	public Comment addComment(long articleId, String content, Account account) {
		Comment c = new Comment();
		c.setArticleId(articleId);
		c.setContent(content);
		c.setAccount(account);
		c = commentRepo.save(c);
		
		// No need to manually update article comments count because of the triggers on the
		// database which do such worh automatically when new comment has been added.
		/*
		Article a = articleRepo.getById(articleId);
		a.setNumberOfComments((int)commentRepo.getCountByArticle(articleId));
		articleRepo.update(a);
		*/
		
		// TODO: send notification about new comment has been created
		return c;
	}

	private int calculateOffset(int pageNumber, int pageSize) {
		return (pageNumber - 1) * pageSize;
	}

	private static void validate(int pageNumber, int pageSize) {
		Checks.checkParam(pageNumber >= 1, "page number can not be less that 1: %d", pageNumber);
		Checks.checkParam(pageSize >= 1, "page size can not be less that 1: %d", pageSize);
	}
}
