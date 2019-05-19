package com.revenat.iblog.persistence.repository.jdbc;

final class SqlQueries {
	public static final String GET_ALL_CATEGORIES = "SELECT * FROM category ORDER BY name";
	public static final String GET_ALL_CATEGORIES_BY_SEARCH_QUERY =  " SELECT cat.id, cat.name, cat.url, count(a.id) AS articles FROM category AS cat"
			+ " LEFT OUTER JOIN (SELECT ar.id, ar.category_id FROM article AS ar"
			+ " WHERE (ar.title ILIKE ? OR ar.content ILIKE ?)) AS a"
			+ " ON cat.id = a.category_id"
			+ " GROUP BY cat.id, cat.url, cat.name"
			+ " ORDER BY cat.name";
	public static final String GET_CATEGORY_BY_URL = "SELECT * FROM category WHERE url = ?";
	
	public static final String GET_ARTICLE_BY_ID = "SELECT * FROM article WHERE id = ?";
	public static final String UPDATE_ARTICLE = "UPDATE article SET views = ?, comments = ? WHERE id = ?";
	public static final String GET_ALL_ARTICLES = "SELECT * FROM article ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String GET_ARTICLES_BY_CATEGORY =
			"SELECT * FROM article WHERE category_id = ? ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String GET_ARTICLES_BY_SEARCH_QUERY =
			"SELECT * FROM article WHERE (title ILIKE ? OR content ILIKE ?) ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String GET_ARTICLES_BY_SEARCH_QUERY_AND_CATEGORY =
			"SELECT * FROM article WHERE (title ILIKE ? OR content ILIKE ?) AND category_id = ? ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String COUNT_ALL_ARTICLES = "SELECT count(*) AS count FROM article";
	public static final String COUNT_ARTICLES_BY_CATEGORY = "SELECT count(*) AS count FROM article WHERE category_id = ?";
	public static final String COUNT_ARTICLES_BY_SEARCH_QUERY = "SELECT count(*) AS count FROM article WHERE (title ILIKE ? OR content ILIKE ?)";
	public static final String COUNT_ARTICLES_BY_SEARCH_QUERY_AND_CATEGORY =
			"SELECT count(*) AS count FROM article WHERE (title ILIKE ? OR content ILIKE ?) AND category_id = ?";
	
	public static final String GET_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id = ?";
	public static final String GET_ACCOUNT_BY_EMAIL = "SELECT * FROM account WHERE email = ?";
	public static final String INSERT_INTO_ACCOUNT = "INSERT INTO account (email, name, avatar, created) VALUES (?,?,?,?)";
	
	public static final String GET_COMMENT_BY_ARTICLE_ID = 
			"SELECT * FROM comment WHERE article_id = ? ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String GET_COMMENT_BY_ARTICLE_ID_JOIN = 
			"SELECT c.*, a.email, a.name, a.avatar, a.created AS accountCreated"
			+ " FROM comment AS c INNER JOIN account AS a ON c.account_id = a.id"
			+ " WHERE c.article_id = ? ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String COUNT_COMMENTS_BY_ARTICLE = "SELECT count(*) AS count FROM comment WHERE article_id = ?";
	public static final String INSERT_INTO_COMMENT = "INSERT INTO comment (account_id, article_id, content, created)"
			+ " VALUES (?,?,?,?)";

	private SqlQueries() {}
}
