package com.revenat.iblog.persistence.repository.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.persistence.infra.util.JDBCUtils.ResultSetHandler;

/**
 * This factory class responsible for creating different
 * {@link ResultSetHandler} implementations.
 * 
 * @author Vitaly Dragun
 *
 */
final class MapperFactory {
	
	private static final ResultSetHandler<Category> CATEGORY_HANDLER = rs -> {
		Category c = new Category();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setUrl(rs.getString("url"));
		c.setNumberOfArticles(rs.getInt("articles"));
		return c;
	};
	
	public static ResultSetHandler<List<Category>> getCategoriesMapper() {
		return getMultipleRowsMapper(CATEGORY_HANDLER);
	}

	
	/**
	 * Returns {@link ResultSetHandler} implementation that capable of tarnsforming
	 * {@link ResultSet} data into single instance of specified type.
	 * 
	 * @param oneRowResultSetHandler {@link ResultSetHandler} implementation that
	 *                               can transform {@link ResultSet} row data into
	 *                               instance of specified type.
	 */
	private static final <T> ResultSetHandler<T> getSingleRowMapper(
			final ResultSetHandler<T> oneRowResultSetHandler) {
		return rs -> {
			if (rs.next()) {
				return oneRowResultSetHandler.handle(rs);
			}
			return null;
		};
	}
	
	/**
	 * Returns {@link ResultSetHandler} implementation that capable of tarnsforming
	 * {@link ResultSet} data into {@link List} with instances of specified type.
	 * 
	 * @param oneRowResultSetHandler {@link ResultSetHandler} implementation that
	 *                               can transform {@link ResultSet} row data into
	 *                               instance of specified type.
	 */
	private static final <T> ResultSetHandler<List<T>> getMultipleRowsMapper(
			final ResultSetHandler<T> oneRowResultSetHandler) {
		return rs -> {
			List<T> list = new ArrayList<>();
			while (rs.next()) {
				list.add(oneRowResultSetHandler.handle(rs));
			}
			return list;
		};
	}
	
	private MapperFactory() {}
}
