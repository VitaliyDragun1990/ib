package com.revenat.iblog.ui.controller.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.dto.CommentDTO;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.ui.config.Constants;
import com.revenat.iblog.ui.config.Constants.Attribute;
import com.revenat.iblog.ui.config.Constants.Fragment;
import com.revenat.iblog.ui.controller.AbstractController;

public class MoreCommentsController extends AbstractController {
	private static final long serialVersionUID = -7458611722964713925L;
	
	private final ArticleService articleService;

	public MoreCommentsController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int offset = getOffset(req);
		long articleId = Long.parseLong(req.getParameter("articleId"));
		List<CommentDTO> comments = articleService.loadCommentsForArticle(articleId, offset, Constants.MAX_COMMENTS_PER_PAGE);
		
		req.setAttribute(Attribute.COMMENTS, comments);
		forwardToFragment(Fragment.COMMENTS, req, resp);
	}

	private int getOffset(HttpServletRequest req) {
		String offset = req.getParameter("offset");
		try {
			return Integer.parseInt(offset);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
