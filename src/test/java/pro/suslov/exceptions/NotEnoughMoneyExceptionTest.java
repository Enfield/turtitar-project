package pro.suslov.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NotEnoughMoneyExceptionTest {

	@Test
	void testThrows() {
		Executable closureContainingCodeToTest = () -> {
			throw new NotEnoughMoneyException("NotEnoughMoneyException");
		};
		assertThrows(NotEnoughMoneyException.class, closureContainingCodeToTest, "NotEnoughMoneyException");
	}

}