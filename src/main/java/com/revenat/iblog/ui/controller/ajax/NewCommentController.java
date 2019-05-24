package com.revenat.iblog.ui.controller.ajax;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.dto.CommentDTO;
import com.revenat.iblog.application.form.CommentForm;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.ui.config.Constants.Attribute;
import com.revenat.iblog.ui.config.Constants.Fragment;
import com.revenat.iblog.ui.controller.AbstractController;

public class NewCommentController extends AbstractController {
	private static final long serialVersionUID = 1764993478783499111L;
	
	private final ArticleService articleService;

	public NewCommentController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommentForm form = createForm(req, CommentForm.class);
		
		CommentDTO comment = 
				articleService.addCommentToArticle(form);
		
		req.setAttribute(Attribute.COMMENTS, Collections.singletonList(comment));
		forwardToFragment(Fragment.COMMENTS, req, resp);
	}
}
