package com.revenat.iblog.persistence.repository.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.persistence.infra.util.JDBCUtils;
import com.revenat.iblog.persistence.infra.util.JDBCUtils.ResultSetHandler;
import com.revenat.iblog.persistence.repository.ArticleRepository;

public class JdbcArticleRepository extends AbstractJdbcRepository implements ArticleRepository {
	private static final ResultSetHandler<List<Article>> ARTICLES_MAPPER = MapperFactory.getArticlesMapper();
	private static final ResultSetHandler<Long> COUNT_MAPPER = MapperFactory.getCountMapper();

	public JdbcArticleRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Article> getAll(int offset, int limit) {
		return executeSelect(
				conn -> JDBCUtils.select(conn, SqlQueries.GET_ALL_ARTICLES, ARTICLES_MAPPER, limit, offset));
	}

	@Override
	public List<Article> getByCategory(int categoryId, int offset, int limit) {
		return executeSelect(conn ->
		JDBCUtils.select(conn, SqlQueries.GET_ARTICLES_BY_CATEGORY, ARTICLES_MAPPER, categoryId, limit, offset));
	}
	
	@Override
	public long getTotalCount() {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.COUNT_ALL_ARTICLES, COUNT_MAPPER));
	}

	@Override
	public long getCountByCategory(int categoryId) {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.COUNT_ARTICLES_BY_CATEGORY, COUNT_MAPPER, categoryId));
	}
}
