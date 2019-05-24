package com.revenat.iblog.application.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.revenat.iblog.application.dto.ArticleDTO;
import com.revenat.iblog.application.dto.CommentDTO;
import com.revenat.iblog.application.form.CommentForm;
import com.revenat.iblog.application.model.Items;
import com.revenat.iblog.application.service.AccountService;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.application.service.AuthenticationService;
import com.revenat.iblog.application.service.FeedbackService;
import com.revenat.iblog.application.transform.Transformer;
import com.revenat.iblog.domain.entity.AbstractEntity;
import com.revenat.iblog.domain.entity.Article;
import com.revenat.iblog.domain.entity.Category;
import com.revenat.iblog.domain.entity.Comment;
import com.revenat.iblog.domain.search.criteria.ArticleCriteria;
import com.revenat.iblog.infrastructure.repository.ArticleRepository;
import com.revenat.iblog.infrastructure.repository.CategoryRepository;
import com.revenat.iblog.infrastructure.repository.CommentRepository;
import com.revenat.iblog.infrastructure.transform.Transformable;
import com.revenat.iblog.infrastructure.util.Checks;

/**
 * This component performs different operations on {@link Article} entity.
 * 
 * @author Vitaly Dragun
 *
 */
class ArticleServiceImpl implements ArticleService {
	protected final ArticleRepository articleRepo;
	private final CategoryRepository categoryRepo;
	private final CommentRepository commentRepo;
	protected final AuthenticationService authService;
	protected final AccountService accountService;
	protected final FeedbackService feedbackService;
	private final Transformer transformer;

	public ArticleServiceImpl(ArticleRepository articleRepo, CategoryRepository categoryRepo,
			CommentRepository commentRepo, AuthenticationService authService, AccountService accountService,
			FeedbackService feedbackService, Transformer transformer) {
		this.articleRepo = articleRepo;
		this.categoryRepo = categoryRepo;
		this.commentRepo = commentRepo;
		this.authService = authService;
		this.accountService = accountService;
		this.feedbackService = feedbackService;
		this.transformer = transformer;
	}

	@Override
	public Items<ArticleDTO> listArticles(int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);

		List<Article> articles = articleRepo.getAll(calculateOffset(pageNumber, pageSize), pageSize);
		long totalArticles = articleRepo.getTotalCount();
		
		List<ArticleDTO> dtoList = transform(articles, ArticleDTO.class);

		return new Items<>(dtoList, totalArticles);
	}

	@Override
	public Items<ArticleDTO> listArticlesByCategory(String categoryUrl, int pageNumber, int pageSize) {
		validate(pageNumber, pageSize);

		Category category = categoryRepo.getByUrl(categoryUrl);
		Checks.checkResource(category, "There is no category for such url: %s", categoryUrl);
		List<Article> articles = articleRepo.getByCategory(category.getId(), calculateOffset(pageNumber, pageSize),
				pageSize);
		long countByCategory = articleRepo.getCountByCategory(category.getId());
		
		List<ArticleDTO> dtoList = transform(articles, ArticleDTO.class);

		return new Items<>(dtoList, countByCategory);
	}

	@Override
	public Items<ArticleDTO> listArticlesBySearchQuery(String searchQuery, String categoryUrl, int pageNumber,
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
		List<ArticleDTO> dtoList = transform(articles, ArticleDTO.class);
		long countByCriteria = articleRepo.getCountByCriteria(searchCriteria);

		return new Items<>(dtoList, countByCriteria);
	}

	@Override
	public ArticleDTO getArticle(long articleId) {
		Article article = articleRepo.getById(articleId);
		Checks.checkResource(article, "Article with id: %d not found", articleId);
		return transform(article, ArticleDTO.class);
	}

	@Override
	public ArticleDTO incrementArticleViewCount(long articleId) {
		Article a = articleRepo.getById(articleId);
		a.setNumberOfViews(a.getNumberOfViews() + 1);
		articleRepo.update(a);
		return transform(a, ArticleDTO.class);
	}

	@Override
	public List<CommentDTO> loadCommentsForArticle(long articleId, int offset, int pageSize) {
		Checks.checkParam(offset >= 0, "offset can not be less that 0: %d", pageSize);
		Checks.checkParam(pageSize >= 1, "page size can not be less that 1: %d", pageSize);
		List<Comment> comments = commentRepo.getByArticle(articleId, offset, pageSize);
		return transform(comments, CommentDTO.class,
				(entity,dto) -> dto.setAccount(accountService.getById(entity.getAccountId())));
	}

	@Override
	public CommentDTO addCommentToArticle(CommentForm form) {
		form.validate();
		long accountId = authService.authenticate(form.getAuthToken());
		Comment c = new Comment();
		c.setArticleId(form.getArticleId());
		c.setContent(form.getContent());
		c.setAccountId(accountId);
		c = commentRepo.save(c);
		/*
		 * No need to manually update article comments count because of the triggers on
		 * the database which do such worh automatically when new comment has been
		 * added.
		 *
		 * Article a = articleRepo.getById(articleId);
		 * a.setNumberOfComments((int)commentRepo.getCountByArticle(articleId));
		 * articleRepo.update(a);
		 */

		feedbackService.sendNewCommentNotification(form);
		return transform(c, CommentDTO.class,
				(entity, dto) -> dto.setAccount(accountService.getById(entity.getAccountId())));
	}
	
	protected <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> List<P> transform(List<T> entities, Class<P> clz) {
		return transform(entities, clz, (entity,dto) -> {});
	}
	
	protected <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> List<P> transform(List<T> entities, Class<P> clz, BiConsumer<T,P> cons) {
		return entities.stream()
				.map(entity -> {
					P dto = transformer.transform(entity, clz);
					cons.accept(entity,dto);
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	protected <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> P transform(final T entity, Class<P> clz) {
		return transform(entity, clz, (e,dto) -> {});
	}
	
	protected <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> P transform(T entity, Class<P> clz, BiConsumer<T,P> cons) {
		P dto = transformer.transform(entity, clz);
		cons.accept(entity,dto);
		return dto;
	}

	private int calculateOffset(int pageNumber, int pageSize) {
		return (pageNumber - 1) * pageSize;
	}

	private static void validate(int pageNumber, int pageSize) {
		Checks.checkParam(pageNumber >= 1, "page number can not be less that 1: %d", pageNumber);
		Checks.checkParam(pageSize >= 1, "page size can not be less that 1: %d", pageSize);
	}
}
