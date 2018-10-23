package pro.suslov.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.suslov.db.DataSource;
import pro.suslov.entity.Account;
import pro.suslov.exceptions.DataBaseRuntimeException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryThrowableTest {
	@Mock
	private DataSource dataSource;

	@InjectMocks
	private AccountRepository accountRepository;

	@Test
	void insert() {
		given(dataSource.getConnection(any())).willAnswer(invocation -> {
			throw new SQLException();
		});
		Executable closureContainingCodeToTest = () -> {
			accountRepository.insert(new Account.Builder().build());
		};
		assertThrows(DataBaseRuntimeException.class, closureContainingCodeToTest);
	}

	@Test
	void find() {
		given(dataSource.getConnection(any())).willAnswer(invocation -> {
			throw new SQLException();
		});
		Executable closureContainingCodeToTest = () -> {
			accountRepository.find(any());
		};
		assertThrows(DataBaseRuntimeException.class, closureContainingCodeToTest);
	}
}