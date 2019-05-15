package com.revenat.iblog.persistence.repository.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.persistence.infra.util.JDBCUtils;
import com.revenat.iblog.persistence.infra.util.JDBCUtils.ResultSetHandler;
import com.revenat.iblog.persistence.repository.CategoryRepository;

public class JdbcCategoryRepository extends AbstractJdbcRepository implements CategoryRepository {
	private static final ResultSetHandler<List<Category>> CATEGORIES_MAPPER =
			MapperFactory.getCategoriesMapper();

	public JdbcCategoryRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Category> getAll() {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.GET_ALL_CATEGORIES, CATEGORIES_MAPPER));
	}
}
