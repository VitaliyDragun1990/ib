package com.revenat.iblog.presentation.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.domain.form.AbstractForm;
import com.revenat.iblog.application.infra.exception.flow.FlowException;
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
	
	protected final <T extends AbstractForm> T createForm(HttpServletRequest req, Class<T> formClass) throws ServletException {
		try {
			T form = formClass.newInstance();
			form.setLocale(req.getLocale());
			BeanUtils.populate(form, req.getParameterMap());
			return form;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new FlowException("Can not create form " + formClass + " for request: " + e.getMessage(), e);
		}
	}
}
