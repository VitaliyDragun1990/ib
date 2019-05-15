package com.revenat.iblog.persistence.repository;

import javax.sql.DataSource;

import com.revenat.iblog.persistence.repository.jdbc.JdbcCategoryRepository;

public class RepositoryFactory {
	private final DataSource dataSource;

	public RepositoryFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public CategoryRepository createCategoryRepository() {
		return new JdbcCategoryRepository(dataSource);
	}
}
