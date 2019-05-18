package com.revenat.iblog.persistence.repository.jdbc;

import javax.sql.DataSource;

import com.revenat.iblog.application.domain.entity.Account;
import com.revenat.iblog.persistence.infra.util.JDBCUtils;
import com.revenat.iblog.persistence.infra.util.JDBCUtils.ResultSetHandler;
import com.revenat.iblog.persistence.repository.AccountRepository;

public class JdbcAccountRepository extends AbstractJdbcRepository implements AccountRepository {
	private static final ResultSetHandler<Account> ACCOUNT_MAPPER = MapperFactory.getAccountMapper();

	public JdbcAccountRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Account getById(long id) {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.GET_ACCOUNT_BY_ID, ACCOUNT_MAPPER, id));
	}
}
