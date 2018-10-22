package pro.suslov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.suslov.entity.Account;
import pro.suslov.entity.Transaction;
import pro.suslov.exceptions.ValidationException;
import pro.suslov.repository.AccountRepository;
import pro.suslov.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionService transactionService = new TransactionService();


	@Test
	void createWithIncorrectParameters() {
		BigDecimal amount = new BigDecimal(0.1234);
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from(UUID.randomUUID().toString()).build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().to(UUID.randomUUID().toString()).build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from("1").build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().to("1").build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from("").build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().to("").build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from(null).build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().to(null).build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from("1").to("1").build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from("").to("").build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from(null).to(null).build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().amount(amount).build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from(UUID.randomUUID().toString()).to(UUID.randomUUID().toString()).amount(null).build()));
		assertThrows(ValidationException.class, () -> transactionService.create(new Transaction.Builder().from(UUID.randomUUID().toString()).to(UUID.randomUUID().toString()).amount(new BigDecimal(-9.12345)).build()));
	}

	@Test
	void create() {
		String from = UUID.randomUUID().toString();
		String to = UUID.randomUUID().toString();
		BigDecimal amount = new BigDecimal(0.1234);
		Transaction transaction = new Transaction.Builder().from(from).to(to).amount(amount).build();
		when(transactionRepository.insert(transaction)).thenReturn(true);
		boolean result = transactionService.create(transaction);
		assertTrue(result);
	}

	@Test
	void createSame() {
		AccountRepository accountRepository = new AccountRepository();
		Account account = accountRepository.insert(new Account.Builder().amount(new BigDecimal(1000.1234)).build());

		BigDecimal amount = new BigDecimal(0.1234);
		Transaction transaction = new Transaction.Builder().from(account.getId()).to(account.getId()).amount(amount).build();
		Executable closureContainingCodeToTest = () -> transactionService.create(transaction);
		assertThrows(ValidationException.class, closureContainingCodeToTest);
	}
}