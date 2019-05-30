package pro.suslov.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.suslov.db.DataSource;
import pro.suslov.entity.Account;
import pro.suslov.exceptions.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountRepository {
	private static final Logger log = LoggerFactory.getLogger(AccountRepository.class);
	private DataSource dataSource = new DataSource();

	public Account insert(Account account) {
		var sql = "insert into accounts (id, amount) values (?, ?)";
		account.setId(UUID.randomUUID().toString());
		log.debug("Insert account with parameters {id:%s amount:%s}'", account.getId(), account.getAmount());
		try (var conn = dataSource.getConnection(DataSource.INIT_STRING);
			 var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, account.getId());
			stmt.setBigDecimal(2, account.getAmount());
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			throw new DataBaseRuntimeException(e);
		}
		return account;
	}

	public Account find(String id) {
		var sql = "select id, amount from accounts where id = ?";
		log.debug("Find account by {id:%s}'", id);
		try (var conn = dataSource.getConnection(DataSource.INIT_STRING);
			 var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			var rs = stmt.executeQuery();
			if (rs.next()) {
				return new Account.Builder()
						.id(rs.getString(1))
						.amount(rs.getBigDecimal(2))
						.build();
			}
			return null;
		} catch (SQLException e) {
			throw new DataBaseRuntimeException(e);
		}
	}
}
