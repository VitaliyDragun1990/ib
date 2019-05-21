package com.revenat.iblog.application.service;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.application.domain.entity.Comment;
import com.revenat.iblog.application.domain.form.CommentForm;
import com.revenat.iblog.application.domain.model.Items;
import com.revenat.iblog.application.infra.exception.ResourceNotFoundException;

public interface ArticleService {

	/**
	 * Returns {@link Items} object with {@link Article} instances for given page.
	 * 
	 * @param pageNumber page for which articles are to be returned.
	 * @param pageSize   max number of articles per page
	 * @return {@link Items} with articles for given page or empty one if no
	 *         articles were found.
	 */
	Items<Article> listArticles(int pageNumber, int pageSize);

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
	Items<Article> listArticlesByCategory(String categoryUrl, int pageNumber, int pageSize);

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
	Items<Article> listArticlesBySearchQuery(String searchQuery, String categoryUrl, int pageNumber, int pageSize);

	/**
	 * Finds {@link Article} using specified {@code articleId} parameter.
	 * 
	 * @param articleId if of the article to find.
	 * @return article with such id.
	 * @throws ResourceNotFoundException if no article with specified id was found.
	 */
	Article findArticle(long articleId);

	/**
	 * Increments {@link Article#getNumberOfViews()} attribute by {@code 1} and
	 * persist such change to the datastore.
	 * 
	 * @return article with incremented view count
	 */
	Article incrementArticleViewCount(long articleId);

	/**
	 * Loads more {@link Comment}s for {@link Article} denoted by specified
	 * {@code articleId}.
	 * 
	 * @param articleId id of the article to load comments for
	 * @param offset    number of comments to ommit from the start
	 * @param pageSize  max number of comments to load
	 * @return list with comments for specified article, or empty one if no comments
	 *         were found.
	 */
	List<Comment> loadCommentsForArticle(long articleId, int offset, int pageSize);

	/**
	 * Adds new comment for particular article.
	 * 
	 * @param form contains information about comment to be created
	 * @return newly created comment
	 */
	Comment addCommentToArticle(CommentForm form, String articleUri);

}