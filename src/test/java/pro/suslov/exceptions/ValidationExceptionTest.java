package pro.suslov.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationExceptionTest {

	@Test
	void testThrows() {
		Executable closureContainingCodeToTest = () -> {
			throw new ValidationException("ValidationException");
		};
		assertThrows(ValidationException.class, closureContainingCodeToTest, "ValidationException");
	}

}