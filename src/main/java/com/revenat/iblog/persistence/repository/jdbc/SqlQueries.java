package com.revenat.iblog.persistence.repository.jdbc;

final class SqlQueries {
	public static final String GET_ALL_CATEGORIES = "SELECT * FROM category ORDER BY name";
	public static final String GET_CATEGORY_BY_URL = "SELECT * FROM category WHERE url = ?";
	public static final String GET_ALL_ARTICLES = "SELECT * FROM article ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String GET_ARTICLES_BY_CATEGORY =
			"SELECT * FROM article WHERE category_id = ? ORDER BY created DESC LIMIT ? OFFSET ?";
	public static final String COUNT_ALL_ARTICLES = "SELECT count(*) AS count FROM article";
	public static final String COUNT_ARTICLES_BY_CATEGORY = "SELECT count(*) AS count FROM article WHERE category_id = ?";

	private SqlQueries() {}
}
