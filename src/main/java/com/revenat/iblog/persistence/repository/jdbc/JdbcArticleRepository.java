package com.revenat.iblog.persistence.repository.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.search.criteria.ArticleCriteria;
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
	
	@Override
	public List<Article> getByCriteria(ArticleCriteria searchCriteria, int offset, int limit) {
		SqlQuery query = buildSelectQuery(searchCriteria, limit, offset);
		
		return executeSelect(conn -> JDBCUtils.select(conn, query.getQuery(), ARTICLES_MAPPER, query.getParams()));
	}
	
	@Override
	public long getCountByCriteria(ArticleCriteria searchCriteria) {
		SqlQuery query = buildCountQuery(searchCriteria);
		return executeSelect(conn -> JDBCUtils.select(conn, query.getQuery(), COUNT_MAPPER, query.getParams()));
	}
	
	private SqlQuery buildSelectQuery(ArticleCriteria criteria, Object... params) {
		String query = null;
		List<Object> allParams = new ArrayList<>();
		allParams.add(getLikeParam(criteria.getQuery()));
		allParams.add(getLikeParam(criteria.getQuery()));
		
		if (criteria.isCategorySpecified()) {
			query = SqlQueries.GET_ARTICLES_BY_SEARCH_QUERY_AND_CATEGORY;
			allParams.add(criteria.getCategoryId());
		} else {
			query = SqlQueries.GET_ARTICLES_BY_SEARCH_QUERY;
		}
		allParams.addAll(Arrays.asList(params));
		return new SqlQuery(query, allParams.toArray());
	}
	
	private SqlQuery buildCountQuery(ArticleCriteria criteria) {
		String query = null;
		List<Object> allParams = new ArrayList<>();
		allParams.add(getLikeParam(criteria.getQuery()));
		allParams.add(getLikeParam(criteria.getQuery()));
		
		if (criteria.isCategorySpecified()) {
			query = SqlQueries.COUNT_ARTICLES_BY_SEARCH_QUERY_AND_CATEGORY;
			allParams.add(criteria.getCategoryId());
		} else {
			query = SqlQueries.COUNT_ARTICLES_BY_SEARCH_QUERY;
		}
		return new SqlQuery(query, allParams.toArray());
	}
	
	private String getLikeParam(String query) {
		return "%" + query + "%";
	}
	
	static class SqlQuery {
		private final String query;
		private final Object[] params;
		
		public SqlQuery(String query, Object... params) {
			this.query = query;
			this.params = params;
		}

		public String getQuery() {
			return query;
		}

		public Object[] getParams() {
			return params;
		}
	}
}
