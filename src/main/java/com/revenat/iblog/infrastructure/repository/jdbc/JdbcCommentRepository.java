package com.revenat.iblog.infrastructure.repository.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.revenat.iblog.domain.entity.Comment;
import com.revenat.iblog.infrastructure.repository.CommentRepository;
import com.revenat.iblog.infrastructure.util.JDBCUtils;
import com.revenat.iblog.infrastructure.util.JDBCUtils.ResultSetHandler;

public class JdbcCommentRepository extends AbstractJdbcRepository implements CommentRepository {
	private static final ResultSetHandler<List<Comment>> COMMENTS_MAPPER = MapperFactory.getCommentsMapper();
	private static final ResultSetHandler<Long> COUNT_MAPPER = MapperFactory.getCountMapper();
	private static final ResultSetHandler<Long> ID_MAPPER = MapperFactory.GENERATED_LONG_ID_HANDLER;
	
	public JdbcCommentRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Comment> getByArticle(long articleId, int offset, int limit) {
		return executeSelect(
				conn -> JDBCUtils.select(conn, SqlQueries.GET_COMMENT_BY_ARTICLE_ID, COMMENTS_MAPPER, articleId, limit, offset));
	}

	@Override
	public long getCountByArticle(long articleId) {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.COUNT_COMMENTS_BY_ARTICLE, COUNT_MAPPER, articleId));
	}
	
	@Override
	public Comment save(Comment comment) {
		long commentId = executeUpdate(conn -> JDBCUtils.insert(conn, SqlQueries.INSERT_INTO_COMMENT, ID_MAPPER,
				comment.getAccountId(), comment.getArticleId(), comment.getContent(), comment.getCreated()));
		comment.setId(commentId);
		return comment;
	}
}
