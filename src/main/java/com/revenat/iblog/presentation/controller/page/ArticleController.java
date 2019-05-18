package com.revenat.iblog.presentation.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.infra.exception.ResourceNotFoundException;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Page;

public class ArticleController extends AbstractController {
	private static final long serialVersionUID = -6579422407941195197L;

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			processRequest(req, resp);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new ResourceNotFoundException("There is no resource for such url: " + req.getRequestURI());
		}
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String requestUri = req.getRequestURI();
		Article article = findArticle(getArticleId(requestUri));
		if (!getArticleUrl(requestUri).equals(article.getUrl())) {
			redirect(getArticleLink(article), resp);
		} else {
			articleService.incrementArticleViewCount(article);
			req.setAttribute(Attribute.ARTICLE, article);
			forwardToPage(Page.ARTILCE, req, resp);
		}
	}

	private Article findArticle(String articleId) {
		return articleService.findArticle(Long.parseLong(articleId));
	}

	private String getArticleUrl(String requestUri) {
		return "/" + StringUtils.split(requestUri, "/")[2];
	}

	private String getArticleId(String requestUri) {
		return StringUtils.split(requestUri, "/")[1];
	}

	private String getArticleLink(Article article) {
		return "/article/" + article.getId() + article.getUrl();
	}
}
