package com.revenat.iblog.presentation.infra.config;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionTrackingMode;

import com.revenat.iblog.application.service.impl.ServiceManager;
import com.revenat.iblog.presentation.controller.ajax.MoreCommentsController;
import com.revenat.iblog.presentation.controller.ajax.NewCommentController;
import com.revenat.iblog.presentation.controller.page.AboutController;
import com.revenat.iblog.presentation.controller.page.ArticleController;
import com.revenat.iblog.presentation.controller.page.ContactController;
import com.revenat.iblog.presentation.controller.page.ErrorController;
import com.revenat.iblog.presentation.controller.page.NewsByCategoryController;
import com.revenat.iblog.presentation.controller.page.NewsController;
import com.revenat.iblog.presentation.controller.page.SearchController;
import com.revenat.iblog.presentation.filter.CategoriesLoaderFilter;
import com.revenat.iblog.presentation.filter.ErrorHandlerFilter;
import com.revenat.iblog.presentation.infra.config.Constants.URL;
import com.revenat.iblog.presentation.listener.ApplicationListener;

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
		ServiceManager serviceManager = ServiceManager.getInstance(ctx.getRealPath("/"));
		
		Dynamic servletReg = ctx.addServlet("NewsController", new NewsController(serviceManager.getArticleService()));
		servletReg.addMapping(URL.NEWS);
		
		servletReg = ctx.addServlet("NewsByCategoryController",
				new NewsByCategoryController(serviceManager.getArticleService(), serviceManager.getCategoryService()));
		servletReg.addMapping(URL.NEWS_BY_CATEGORY);
		
		servletReg = ctx.addServlet("ArtilceController", new ArticleController(serviceManager.getArticleService()));
		servletReg.addMapping(URL.ARTICLE);
		
		
		servletReg = ctx.addServlet("ContactController", new ContactController());
		servletReg.addMapping(URL.CONTACT);
		
		servletReg = ctx.addServlet("AboutController", new AboutController());
		servletReg.addMapping(URL.ABOUT);
		
		servletReg = ctx.addServlet("SearchController",
				new SearchController(serviceManager.getArticleService(), serviceManager.getCategoryService()));
		servletReg.addMapping(URL.SEARCH);
		
		servletReg = ctx.addServlet("ErrorController", new ErrorController());
		servletReg.addMapping(URL.ERROR);
		
		servletReg = ctx.addServlet("MoreCommentsController", new MoreCommentsController(serviceManager.getArticleService()));
		servletReg.addMapping(URL.AJAX_COMMENTS);
		
		servletReg = ctx.addServlet("NewCommentController", new NewCommentController(serviceManager.getArticleService(),
				serviceManager.getAuthService()));
		servletReg.addMapping(URL.AJAX_COMMENT);
		
		FilterRegistration.Dynamic filterReg = ctx.addFilter("ErrorHandlerFilter", new ErrorHandlerFilter());
		filterReg.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST),
				false,
				"/*");
		
		filterReg = ctx.addFilter("CategoriesLoaderFilter", new CategoriesLoaderFilter(serviceManager.getCategoryService()));
		filterReg.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST),
				true,
				"/*");
		
		ctx.addListener(new ApplicationListener(serviceManager));
	}
}
