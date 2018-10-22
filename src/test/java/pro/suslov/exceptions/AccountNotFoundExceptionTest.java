package pro.suslov.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountNotFoundExceptionTest {

	@Test
	void testThrows() {
		Executable closureContainingCodeToTest = () -> {
			throw new AccountNotFoundException("AccountNotFoundException");
		};
		assertThrows(AccountNotFoundException.class, closureContainingCodeToTest, "AccountNotFoundException");
	}

}