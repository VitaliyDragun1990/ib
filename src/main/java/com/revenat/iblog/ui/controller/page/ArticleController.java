package com.revenat.iblog.ui.controller.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.revenat.iblog.application.dto.ArticleDTO;
import com.revenat.iblog.application.dto.CommentDTO;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.infrastructure.exception.ResourceNotFoundException;
import com.revenat.iblog.ui.config.Constants;
import com.revenat.iblog.ui.config.Constants.Attribute;
import com.revenat.iblog.ui.config.Constants.Page;
import com.revenat.iblog.ui.controller.AbstractController;

public class ArticleController extends AbstractController {
	private static final long serialVersionUID = -6579422407941195197L;

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			viewArticle(req, resp);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new ResourceNotFoundException("There is no resource for such url: " + req.getRequestURI());
		}
	}

	private void viewArticle(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String requestUri = req.getRequestURI();
		ArticleDTO article = findArticle(getArticleId(requestUri));
		if (!getArticleUrl(requestUri).equals(article.getUrl())) {
			redirect(article.getArticleLink(), resp);
		} else {
			article = articleService.incrementArticleViewCount(article.getId());
			List<CommentDTO> comments = articleService.loadCommentsForArticle(article.getId(), 0, Constants.MAX_COMMENTS_PER_PAGE);
			
			req.setAttribute(Attribute.ARTICLE, article);
			req.setAttribute(Attribute.COMMENTS, comments);
			forwardToPage(Page.ARTILCE, req, resp);
		}
	}

	private ArticleDTO findArticle(String articleId) {
		return articleService.getArticle(Long.parseLong(articleId));
	}

	private String getArticleUrl(String requestUri) {
		return "/" + StringUtils.split(requestUri, "/")[2];
	}

	private String getArticleId(String requestUri) {
		return StringUtils.split(requestUri, "/")[1];
	}
}
