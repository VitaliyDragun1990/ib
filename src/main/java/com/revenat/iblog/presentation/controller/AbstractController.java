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
	
	public final void forwardToPage(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutingUtils.forwardToPage(jspPage, req, resp);
	}
	
	public final void forwardToFragment(String jspFragment, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutingUtils.forwardToFragment(jspFragment, req, resp);
	}
}
