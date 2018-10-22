package pro.suslov.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.suslov.db.DataSource;
import pro.suslov.entity.Transaction;
import pro.suslov.exceptions.AccountNotFoundException;
import pro.suslov.exceptions.DataBaseRuntimeException;
import pro.suslov.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRepository {
	private static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);
	private DataSource dataSource = new DataSource();

	public boolean insert(Transaction transaction) {
		log.debug("Update amount for accounts {from:%s; to:%s; amount:%s}'", transaction.getFrom(), transaction.getTo());
		try (Connection conn = dataSource.getConnection(DataSource.INIT_STRING)) {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			try {
				//LOCK FROM
				Statement stmtFrom = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				ResultSet rsFrom = stmtFrom.executeQuery("select id, amount from accounts where id = '" + transaction.getFrom() + "' for update");
				if (rsFrom.next()) {
					//LOCK TO
					Statement stmtTo = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
					ResultSet rsTo = stmtTo.executeQuery("select id, amount from accounts where id = '" + transaction.getTo() + "' for update");
					if (rsTo.next()) {
						BigDecimal fromAmount = rsFrom.getBigDecimal(2);
						BigDecimal toAmount = rsTo.getBigDecimal(2);

						//BALANCE CHECK
						if (fromAmount.subtract(transaction.getAmount()).signum() == -1) {
							throw new NotEnoughMoneyException("Not enough money in account {" + transaction.getFrom() + "} for transaction");
						}

						//UPDATE FROM
						rsFrom.updateBigDecimal(2, fromAmount.subtract(transaction.getAmount()));
						rsFrom.updateRow();

						//UPDATE TO
						rsTo.updateBigDecimal(2, toAmount.add(transaction.getAmount()));
						rsTo.updateRow();

						//COMMIT
						conn.commit();
					} else {
						throw new AccountNotFoundException("Can't found account {" + transaction.getTo() + "}");
					}
				} else {
					throw new AccountNotFoundException("Can't found account {" + transaction.getFrom() + "}");
				}
			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new DataBaseRuntimeException(e);
		}
		return true;
	}
}
