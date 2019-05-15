package com.revenat.iblog.application.service;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.persistence.repository.CategoryRepository;
import com.revenat.iblog.persistence.repository.RepositoryFactory;

public class ServiceManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);
	private static final String APPLICATION_PROPERTIES = "application.properties";
	private static ServiceManager instance = null;
	
	private final Properties applicationProperties;
	private final BasicDataSource dataSource;
	private final CategoryService categoryService;
	private final ArticleService articleService;
	
	public CategoryService getCategoryService() {
		return categoryService;
	}
	
	public ArticleService getArticleService() {
		return articleService;
	}
	
	public static synchronized ServiceManager getInstance() {
		if (instance == null) {
			instance = new ServiceManager();
		}
		return instance;
	}
	
	public String getApplicationProperty(String propertyName) {
		return applicationProperties.getProperty(propertyName);
	}
	
	public void close() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			LOGGER.warn("Error while closing datasource: " + e.getMessage(), e);
		}
		LOGGER.info("ServiceManager instance destroyed");
	}
	
	private ServiceManager() {
		applicationProperties = loadApplicationProperties();
		dataSource = createDataSource(
				getApplicationProperty("db.url"),
				getApplicationProperty("db.username"),
				getApplicationProperty("db.password"),
				getApplicationProperty("db.driver"),
				getApplicationProperty("db.pool.initSize"),
				getApplicationProperty("db.pool.maxSize"));
		RepositoryFactory repoFactory = new RepositoryFactory(dataSource);
		CategoryRepository categoryRepository = repoFactory.createCategoryRepository();
		categoryService = new CategoryService(categoryRepository);
		articleService = new ArticleService(repoFactory.createArticleRepository(), categoryRepository);
		
		LOGGER.info("ServiceManager instance created");
	}
	
	private Properties loadApplicationProperties() {
		return new PropertiesLoader().load(APPLICATION_PROPERTIES);
	}
	
	private BasicDataSource createDataSource(String url, String username, String password, String driverClass,
			String poolInitSize, String poolMaxSize) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDefaultAutoCommit(false);
		ds.setRollbackOnReturn(true);
		ds.setDriverClassName(driverClass);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(Integer.parseInt(poolInitSize));
		ds.setMaxTotal(Integer.parseInt(poolMaxSize));

		return ds;
	}
}
