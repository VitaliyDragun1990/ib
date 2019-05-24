package com.revenat.iblog.infrastructure.repository.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.revenat.iblog.infrastructure.exception.PersistenceException;

/**
 * This is the base parent class for all JDBC repositories.
 * 
 * @author Vitaly Dragun
 *
 */
abstract class AbstractJdbcRepository {
	private final DataSource dataSource;

	public AbstractJdbcRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	protected <T> T executeSelect(Function<T> func) {
		try (Connection conn = dataSource.getConnection()) {
			return func.execute(conn);
		} catch (SQLException e) {
			throw new PersistenceException("Error while executing sql query", e);
		}
	}
	
	protected <T> T executeUpdate(Function<T> func) {
		Connection ref = null;
		try (Connection conn = dataSource.getConnection()) {
			ref = conn;
			T result =  func.execute(conn);
			conn.commit();
			return result;
		} catch (SQLException e) {
			rollback(ref, e);
			throw new PersistenceException("Error while executing sql query", e);
		}
	}

	private void rollback(Connection conn, SQLException mainExc) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				mainExc.addSuppressed(e);
			}
		}
	}
	
	@FunctionalInterface
	protected interface Function<T> {
		T execute (Connection conn) throws SQLException;
	}
}
