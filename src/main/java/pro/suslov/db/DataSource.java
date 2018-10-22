package pro.suslov.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.suslov.exceptions.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
	//for test reasons
	public static final String INIT_STRING = "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;LOCK_MODE=3;MULTI_THREADED=1;" +
			"INIT=RUNSCRIPT FROM 'classpath:create.sql';";
	private static final Logger log = LoggerFactory.getLogger(DataSource.class);

	public Connection getConnection(String initString) {
		try {
			return DriverManager.getConnection(initString, "", "");
		} catch (SQLException e) {
			log.error("Failed to get database connection", e);
			throw new DataBaseRuntimeException(e);
		}
	}
}
