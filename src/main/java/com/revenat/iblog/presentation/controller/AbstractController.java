package com.revenat.iblog.presentation.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.presentation.infra.util.RoutingUtils;

public class AbstractController extends HttpServlet {
	private static final long serialVersionUID = -7537501462129325213L;
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	protected final void forwardToPage(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutingUtils.forwardToPage(jspPage, req, resp);
	}
	
	protected final void forwardToFragment(String jspFragment, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutingUtils.forwardToFragment(jspFragment, req, resp);
	}
	
	protected final void redirect(String url, HttpServletResponse resp) throws IOException {
		RoutingUtils.redirect(url, resp);
	}
	
	protected final int getPageNumber(HttpServletRequest req) {
		String page = req.getParameter("page");
		try {
			return Integer.parseInt(page);
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	protected int calculateOffset(int pageNumber, int pageSize) {
		return (pageNumber - 1) * pageSize;
	}
}
