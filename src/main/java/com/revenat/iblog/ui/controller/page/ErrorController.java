package com.revenat.iblog.ui.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.ui.config.Constants.Attribute;
import com.revenat.iblog.ui.config.Constants.Page;
import com.revenat.iblog.ui.config.Constants.URL;
import com.revenat.iblog.ui.controller.AbstractController;

public class ErrorController extends AbstractController {
	private static final long serialVersionUID = 8791642321886486935L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getAttribute(Attribute.STATUS_CODE) != null) {
			forwardToPage(Page.ERROR, req, resp);
		} else {
			redirect(URL.NEWS, resp);
		}
	}
}
