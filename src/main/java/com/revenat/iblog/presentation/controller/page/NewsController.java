package com.revenat.iblog.presentation.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Page;

public class NewsController extends AbstractController {
	private static final long serialVersionUID = -819614170522907816L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryUrl = getSelectedCategoryUrl(req);
		req.setAttribute(Attribute.SELECTED_CATEGORY_URL, categoryUrl);
		forwardToPage(Page.NEWS, req, resp);
	}

	private String getSelectedCategoryUrl(HttpServletRequest req) {
		String requestURI = req.getRequestURI();
		int startIdx = requestURI.lastIndexOf('/');
		return requestURI.substring(startIdx, requestURI.length());
	}
}
