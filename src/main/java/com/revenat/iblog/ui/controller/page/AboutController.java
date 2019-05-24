package com.revenat.iblog.ui.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.ui.config.Constants.Page;
import com.revenat.iblog.ui.controller.AbstractController;

public class AboutController extends AbstractController {
	private static final long serialVersionUID = -6579422407941195197L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToPage(Page.ABOUT, req, resp);
	}
}
