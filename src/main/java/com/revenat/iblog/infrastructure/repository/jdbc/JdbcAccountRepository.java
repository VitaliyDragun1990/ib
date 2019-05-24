package com.revenat.iblog.infrastructure.repository.jdbc;

import javax.sql.DataSource;

import com.revenat.iblog.domain.entity.Account;
import com.revenat.iblog.infrastructure.repository.AccountRepository;
import com.revenat.iblog.infrastructure.util.JDBCUtils;
import com.revenat.iblog.infrastructure.util.JDBCUtils.ResultSetHandler;

public class JdbcAccountRepository extends AbstractJdbcRepository implements AccountRepository {
	private static final ResultSetHandler<Account> ACCOUNT_MAPPER = MapperFactory.getAccountMapper();
	private static final ResultSetHandler<Long> ID_MAPPER = MapperFactory.GENERATED_LONG_ID_HANDLER;

	public JdbcAccountRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Account getById(long id) {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.GET_ACCOUNT_BY_ID, ACCOUNT_MAPPER, id));
	}
	
	@Override
	public Account getByEmail(String email) {
		return executeSelect(conn -> JDBCUtils.select(conn, SqlQueries.GET_ACCOUNT_BY_EMAIL, ACCOUNT_MAPPER, email));
	}
	
	@Override
	public Account save(Account account) {
		long accountId = executeUpdate(conn -> JDBCUtils.insert(conn, SqlQueries.INSERT_INTO_ACCOUNT, ID_MAPPER,
				account.getEmail(), account.getName(), account.getAvatar(), account.getCreated()));
		account.setId(accountId);
		return account;
	}
}
