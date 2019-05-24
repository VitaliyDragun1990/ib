package com.revenat.iblog.infrastructure.repository;

import java.util.List;

import com.revenat.iblog.domain.entity.Comment;

public interface CommentRepository {

	List<Comment> getByArticle(long articleId, int offset, int limit);
	
	long getCountByArticle(long articleId);
	
	Comment save(Comment comment);
}
