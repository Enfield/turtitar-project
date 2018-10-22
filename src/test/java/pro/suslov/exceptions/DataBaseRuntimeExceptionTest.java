package pro.suslov.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DataBaseRuntimeExceptionTest {
	@Test
	void testThrows() {
		Executable closureContainingCodeToTest = () -> {
			throw new DataBaseRuntimeException(new RuntimeException("DataBaseRuntimeException"));
		};
		assertThrows(DataBaseRuntimeException.class, closureContainingCodeToTest, "DataBaseRuntimeException");
	}
}