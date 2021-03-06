package com.revenat.iblog.infrastructure.repository;

import javax.sql.DataSource;

import com.revenat.iblog.infrastructure.repository.jdbc.JdbcAccountRepository;
import com.revenat.iblog.infrastructure.repository.jdbc.JdbcArticleRepository;
import com.revenat.iblog.infrastructure.repository.jdbc.JdbcCategoryRepository;
import com.revenat.iblog.infrastructure.repository.jdbc.JdbcCommentRepository;

public class RepositoryFactory {
	private final DataSource dataSource;

	public RepositoryFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public CategoryRepository createCategoryRepository() {
		return new JdbcCategoryRepository(dataSource);
	}
	
	public ArticleRepository createArticleRepository() {
		return new JdbcArticleRepository(dataSource);
	}
	
	public AccountRepository createAccountRepository() {
		return new JdbcAccountRepository(dataSource);
	}
	
	public CommentRepository createCommentRepository() {
		return new JdbcCommentRepository(dataSource);
	}
}
