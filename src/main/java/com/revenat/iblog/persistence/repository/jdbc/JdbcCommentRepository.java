package com.revenat.iblog.persistence.repository.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.revenat.iblog.application.domain.entity.Comment;
import com.revenat.iblog.persistence.infra.util.JDBCUtils;
import com.revenat.iblog.persistence.infra.util.JDBCUtils.ResultSetHandler;
import com.revenat.iblog.persistence.repository.CommentRepository;

public class JdbcCommentRepository extends AbstractJdbcRepository implements CommentRepository {
	private static final ResultSetHandler<List<Comment>> COMMENTS_MAPPER = MapperFactory.getCommentsMapper();
	private static final ResultSetHandler<Long> COUNT_MAPPER = MapperFactory.getCountMapper();
	
	public JdbcCommentRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Comment> getByArticle(long articleId, int offset, int limit) {
		return executeSelect(
				conn -> JDBCUtils.select(conn, SqlQueries.GET_COMMENT_BY_ARTICLE_ID_JOIN, COMMENTS_MAPPER, articleId, limit, offset));
	}


	@Override
	public long getCountByArticle(long articleId) {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.COUNT_COMMENTS_BY_ARTICLE, COUNT_MAPPER, articleId));
	}
}
