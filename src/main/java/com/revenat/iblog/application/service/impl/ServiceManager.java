package com.revenat.iblog.application.service.impl;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.service.AccountService;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.application.service.AuthenticationService;
import com.revenat.iblog.application.service.CategoryService;
import com.revenat.iblog.application.service.FeedbackService;
import com.revenat.iblog.application.service.I18nService;
import com.revenat.iblog.application.transform.Transformer;
import com.revenat.iblog.application.transform.impl.FieldProvider;
import com.revenat.iblog.application.transform.impl.SimpleDTOTransformer;
import com.revenat.iblog.infrastructure.repository.AccountRepository;
import com.revenat.iblog.infrastructure.repository.CategoryRepository;
import com.revenat.iblog.infrastructure.repository.RepositoryFactory;
import com.revenat.iblog.infrastructure.service.NotificationService;
import com.revenat.iblog.infrastructure.service.impl.ServiceFactory;

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
	private final NotificationService notificationService;
	private FeedbackService feedbackService;
	
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
	
	public FeedbackService getFeedbackService() {
		return feedbackService;
	}
	
	public static synchronized ServiceManager getInstance(String applicationRootPath) {
		if (instance == null) {
			instance = new ServiceManager(applicationRootPath);
		}
		return instance;
	}
	
	public String getApplicationProperty(String propertyName) {
		String value = applicationProperties.getProperty(propertyName);
		if (value.startsWith("${sysEnv.")) {
			value = value.replace("${sysEnv.", "").replace("}", "");
			return System.getenv(value);
		}
		return value;
	}
	
	public void close() {
		try {
			dataSource.close();
			notificationService.shutdown();
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
		Transformer transformer = new SimpleDTOTransformer(new FieldProvider());
		categoryService = new CategoryServiceImpl(categoryRepository);
		notificationService = ServiceFactory.createNotificationService(getEmailProperties());
		i18nService = new ResourceBundleI18nService(getApplicationProperty("i18n.bundle"));
		feedbackService = new FeedbackServiceImpl(i18nService, notificationService);
		AccountRepository accountRepo = repoFactory.createAccountRepository();
		authService = new SocialAccountAuthenticationService(
				ServiceFactory.createSocialService(getApplicationProperty("social.googleplus.clientId")),
				ServiceFactory.createAvatarService(applicationRootPath),
				accountRepo);
		AccountService accountService = new AccountServiceImpl(accountRepo, transformer);
		articleService = new /*ArticleServiceImpl*/DummyArticleService(repoFactory.createArticleRepository(),
												categoryRepository,
												repoFactory.createCommentRepository(),
												authService,
												accountService,
												feedbackService,
												transformer);
		
		LOGGER.info("ServiceManager instance created");
	}

	private Properties getEmailProperties() {
		Properties emailProps = new Properties();
		emailProps.setProperty("email.notificationEmail", getApplicationProperty("email.notificationEmail"));
		emailProps.setProperty("email.fromEmail", getApplicationProperty("email.fromEmail"));
		emailProps.setProperty("email.sendTryAttempts", getApplicationProperty("email.sendTryAttempts"));
		emailProps.setProperty("email.smtp.server", getApplicationProperty("email.smtp.server"));
		emailProps.setProperty("email.smtp.port", getApplicationProperty("email.smtp.port"));
		emailProps.setProperty("email.smtp.username", getApplicationProperty("email.smtp.username"));
		emailProps.setProperty("email.smtp.password", getApplicationProperty("email.smtp.password"));
		return emailProps;
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
