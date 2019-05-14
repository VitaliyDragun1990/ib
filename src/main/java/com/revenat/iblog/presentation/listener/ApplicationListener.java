package com.revenat.iblog.presentation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.service.ServiceManager;

public class ApplicationListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);
	
	private final ServiceManager serviceManager;
	
	public ApplicationListener(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("Application started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		serviceManager.close();
		LOGGER.info("Application stopped");
	}

}
