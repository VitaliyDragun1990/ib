package com.revenat.iblog.persistence.repository.jdbc;

final class SqlQueries {
	public static final String GET_ALL_CATEGORIES = "SELECT * FROM category ORDER BY name";

	private SqlQueries() {}
}
