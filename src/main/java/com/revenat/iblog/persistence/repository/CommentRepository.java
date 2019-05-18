package com.revenat.iblog.persistence.repository;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Comment;

public interface CommentRepository {

	List<Comment> getByArticle(long articleId, int offset, int limit);
	
	long getCountByArticle(long articleId);
}
