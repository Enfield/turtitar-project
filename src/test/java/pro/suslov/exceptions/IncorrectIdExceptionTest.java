package pro.suslov.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IncorrectIdExceptionTest {
	@Test
	void testThrows() {
		Executable closureContainingCodeToTest = () -> {
			throw new IncorrectIdException("IncorrectIdException");
		};
		assertThrows(IncorrectIdException.class, closureContainingCodeToTest, "IncorrectIdException");
	}
}