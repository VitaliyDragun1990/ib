package com.revenat.iblog.application.service.impl;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.application.service.AuthenticationService;
import com.revenat.iblog.application.service.CategoryService;
import com.revenat.iblog.application.service.I18nService;
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
	private final AuthenticationService authService;
	private final I18nService i18nService;
	
	public CategoryService getCategoryService() {
		return categoryService;
	}
	
	public ArticleService getArticleService() {
		return articleService;
	}
	
	public AuthenticationService getAuthService() {
		return authService;
	}
	
	public I18nService getI18nService() {
		return i18nService;
	}
	
	public static synchronized ServiceManager getInstance(String applicationRootPath) {
		if (instance == null) {
			instance = new ServiceManager(applicationRootPath);
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
	
	private ServiceManager(String applicationRootPath) {
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
		categoryService = new CategoryServiceImpl(categoryRepository);
		articleService = new ArticleServiceImpl(repoFactory.createArticleRepository(),
											categoryRepository,
											repoFactory.createCommentRepository());
		authService = new SocialAccountAuthenticationService(
				new GooglePlusSocialService(getApplicationProperty("social.googleplus.clientId")),
				new FileStorageAvatarService(applicationRootPath),
				repoFactory.createAccountRepository());
		i18nService = new ResourceBundleI18nService(getApplicationProperty("i18n.bundle"));
		
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