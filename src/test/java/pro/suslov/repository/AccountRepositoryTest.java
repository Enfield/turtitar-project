package pro.suslov.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.suslov.db.DataSource;
import pro.suslov.entity.Account;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

	@Mock
	private DataSource dataSource;

	@InjectMocks
	private AccountRepository accountRepository;

	@Test
	void insert() {
		AccountRepository accountRepository = new AccountRepository();
		Account expected = new Account.Builder().amount(new BigDecimal(0.1234)).build();
		Account actual = accountRepository.insert(expected);
		assertEquals(expected.getAmount(), actual.getAmount());
		assertNotNull(actual.getId());
	}

	@Test
	void find() {
		AccountRepository accountRepository = new AccountRepository();
		Account actual = accountRepository.insert(new Account.Builder().amount(new BigDecimal(0.1234)).build());
		assertNotNull(accountRepository.find(actual.getId()));
	}

	@Test
	void findNull() {
		AccountRepository accountRepository = new AccountRepository();
		assertEquals(null, accountRepository.find("1"));
	}

}