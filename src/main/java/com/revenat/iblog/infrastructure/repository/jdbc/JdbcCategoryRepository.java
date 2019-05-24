package com.revenat.iblog.infrastructure.repository.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.revenat.iblog.domain.entity.Category;
import com.revenat.iblog.infrastructure.repository.CategoryRepository;
import com.revenat.iblog.infrastructure.util.JDBCUtils;
import com.revenat.iblog.infrastructure.util.JDBCUtils.ResultSetHandler;

public class JdbcCategoryRepository extends AbstractJdbcRepository implements CategoryRepository {
	private static final ResultSetHandler<List<Category>> CATEGORIES_MAPPER = MapperFactory.getCategoriesMapper();
	private static final ResultSetHandler<Category> CATEGORY_MAPPER = MapperFactory.getCategoryMapper();

	public JdbcCategoryRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Category> getAll() {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.GET_ALL_CATEGORIES, CATEGORIES_MAPPER));
	}
	
	@Override
	public Category getByUrl(String url) {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.GET_CATEGORY_BY_URL, CATEGORY_MAPPER, url));
	}
	
	@Override
	public List<Category> getByArticleSearchQuery(String searchQuery) {
		return executeSelect(conn -> 
		JDBCUtils.select(
				conn, SqlQueries.GET_ALL_CATEGORIES_BY_SEARCH_QUERY,
				CATEGORIES_MAPPER, getLikeParam(searchQuery), getLikeParam(searchQuery)
				));
	}
	
	private String getLikeParam(String query) {
		return "%" + query + "%";
	}
}
