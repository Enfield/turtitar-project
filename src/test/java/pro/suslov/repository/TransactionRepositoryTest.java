package pro.suslov.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pro.suslov.entity.Account;
import pro.suslov.entity.Transaction;
import pro.suslov.exceptions.AccountNotFoundException;
import pro.suslov.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionRepositoryTest {

	@Test
	void insert() {
		AccountRepository accountRepository = new AccountRepository();
		TransactionRepository transactionRepository = new TransactionRepository();
		Account from = accountRepository.insert(new Account.Builder().amount(new BigDecimal(1000.1234)).build());
		Account to = accountRepository.insert(new Account.Builder().amount(new BigDecimal(1000.1234)).build());
		Transaction expected = new Transaction.Builder()
				.from(from.getId())
				.to(to.getId())
				.amount(new BigDecimal(500.1234)).build();
		assertTrue(transactionRepository.insert(expected));
	}

	@Test
	void insertNotFoundFrom() {
		TransactionRepository transactionRepository = new TransactionRepository();
		Transaction expected = new Transaction.Builder()
				.from(UUID.randomUUID().toString())
				.to(UUID.randomUUID().toString())
				.amount(new BigDecimal(0.1234)).build();
		Executable closureContainingCodeToTest = () -> transactionRepository.insert(expected);
		assertThrows(AccountNotFoundException.class, closureContainingCodeToTest);
	}

	@Test
	void insertNotFoundTo() {
		AccountRepository accountRepository = new AccountRepository();
		Account from = accountRepository.insert(new Account.Builder().amount(new BigDecimal(0.1234)).build());

		TransactionRepository transactionRepository = new TransactionRepository();
		Transaction expected = new Transaction.Builder()
				.from(from.getId())
				.to(UUID.randomUUID().toString())
				.amount(new BigDecimal(0.1234)).build();
		Executable closureContainingCodeToTest = () -> transactionRepository.insert(expected);
		assertThrows(AccountNotFoundException.class, closureContainingCodeToTest);
	}

	@Test
	void insertNotEnoughMoney() {
		AccountRepository accountRepository = new AccountRepository();
		TransactionRepository transactionRepository = new TransactionRepository();
		Account from = accountRepository.insert(new Account.Builder().amount(new BigDecimal(499.1234)).build());
		Account to = accountRepository.insert(new Account.Builder().amount(new BigDecimal(1000.1234)).build());
		Transaction expected = new Transaction.Builder()
				.from(from.getId())
				.to(to.getId())
				.amount(new BigDecimal(500.1234)).build();
		Executable closureContainingCodeToTest = () -> transactionRepository.insert(expected);
		assertThrows(NotEnoughMoneyException.class, closureContainingCodeToTest);
	}
}