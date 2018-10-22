package pro.suslov.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pro.suslov.exceptions.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataSourceTest {

	@Test
	void getConnection() throws SQLException {
		DataSource dataSource = new DataSource();
		Connection conn = dataSource.getConnection(DataSource.INIT_STRING);
		assertNotNull(conn);
	}

	@Test
	void getConnectionThrowable() {
		DataSource dataSource = new DataSource();
		Executable closureContainingCodeToTest = () -> dataSource.getConnection("");
		assertThrows(DataBaseRuntimeException.class, closureContainingCodeToTest);
	}
}