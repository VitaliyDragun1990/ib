package com.revenat.iblog.presentation.infra.config;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionTrackingMode;

import com.revenat.iblog.presentation.controller.page.AboutController;
import com.revenat.iblog.presentation.controller.page.ArticleController;
import com.revenat.iblog.presentation.controller.page.ContactController;
import com.revenat.iblog.presentation.controller.page.NewsController;
import com.revenat.iblog.presentation.controller.page.SearchController;
import com.revenat.iblog.presentation.infra.config.Constants.URL;

/**
 * This component creates and configures all servlets, filters, listeners on
 * servlet container startup.
 * 
 * @author Vitaly Dragun
 *
 */
public class ApplicationInitializer implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		ctx.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
		Dynamic servletReg = ctx.addServlet("NewsController", new NewsController());
		servletReg.addMapping(URL.NEWS, URL.NEWS_BY_CATEGORY);
		
		servletReg = ctx.addServlet("ArtilceController", new ArticleController());
		servletReg.addMapping(URL.ARTICLE);
		
		servletReg = ctx.addServlet("ContactController", new ContactController());
		servletReg.addMapping(URL.CONTACT);
		
		servletReg = ctx.addServlet("AboutController", new AboutController());
		servletReg.addMapping(URL.ABOUT);
		
		servletReg = ctx.addServlet("SearchController", new SearchController());
		servletReg.addMapping(URL.SEARCH);
	}
}
