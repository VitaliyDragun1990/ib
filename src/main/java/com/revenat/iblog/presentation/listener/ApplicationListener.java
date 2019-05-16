package com.revenat.iblog.presentation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.service.CategoryService;
import com.revenat.iblog.application.service.ServiceManager;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;

public class ApplicationListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);
	
	private final ServiceManager serviceManager;
	private final CategoryService categoryService;
	
	public ApplicationListener(ServiceManager serviceManager, CategoryService categoryService) {
		this.serviceManager = serviceManager;
		this.categoryService = categoryService;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		sce.getServletContext().setAttribute(Attribute.CATEGORY_MAP, categoryService.getCategoriesWithTotalArticleCount());
		LOGGER.info("Application started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		serviceManager.close();
		LOGGER.info("Application stopped");
	}

}
