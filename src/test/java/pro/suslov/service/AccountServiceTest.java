package pro.suslov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.suslov.entity.Account;
import pro.suslov.exceptions.IncorrectIdException;
import pro.suslov.exceptions.ValidationException;
import pro.suslov.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountService accountService = new AccountService();

	@Test
	void create() {
		String id = UUID.randomUUID().toString();
		BigDecimal amount = new BigDecimal(0.1234);
		when(accountRepository.insert(any())).thenAnswer(a -> {
			Account account = a.getArgument(0);
			account.setId(id);
			return account;
		});

		Account result = accountService.create(new Account.Builder().amount(amount).build());
		assertNotNull(result);
		assertEquals(id, result.getId());
		assertEquals(amount, result.getAmount());

		assertThrows(ValidationException.class, () -> accountService.create(new Account.Builder()
				.amount(null)
				.build()));
		assertThrows(ValidationException.class, () -> accountService.create(new Account.Builder()
				.amount(new BigDecimal(-99.1234))
				.build()));
	}


	@Test
	void getById() {
		String id = UUID.randomUUID().toString();
		BigDecimal amount = new BigDecimal(0.1234);
		when(accountRepository.find(id)).thenReturn(new Account.Builder().id(id).amount(amount).build());
		Account result = accountService.getById(id);
		assertNotNull(result);
		assertNotNull(result);
		assertEquals(id, result.getId());
		assertEquals(amount, result.getAmount());
	}

	@Test
	void getByIncorrectId() {
		Executable closureContainingCodeToTest = () -> accountService.getById("1");
		assertThrows(IncorrectIdException.class, closureContainingCodeToTest);
	}
}