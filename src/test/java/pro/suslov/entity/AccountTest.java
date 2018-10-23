package pro.suslov.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

	@Test
	void getId() {
		String id = UUID.randomUUID().toString();
		Account account = new Account.Builder().id(id).build();
		assertEquals(id, account.getId());
	}

	@Test
	void setId() {
		String id = UUID.randomUUID().toString();
		Account account = new Account();
		account.setId(id);
		assertEquals(id, account.getId());
	}

	@Test
	void getAmount() {
		BigDecimal amount = new BigDecimal(12345.1234);
		Account account = new Account.Builder().amount(amount).build();
		assertEquals(amount, account.getAmount());
	}

	@Test
	void setAmount() {
		BigDecimal amount = new BigDecimal(12345.1234);
		Account account = new Account();
		account.setAmount(amount);
		assertEquals(amount, account.getAmount());
	}

	@Test
	void equals() {
		String id = UUID.randomUUID().toString();
		BigDecimal amount = new BigDecimal(12345.1234);
		Account account1 = new Account.Builder().id(id).amount(amount).build();
		Account account2 = new Account.Builder().id(id).amount(amount).build();
		assertTrue(account1.equals(account2));
		assertFalse(account1.equals(null));
	}

	@Test
	void notEquals() {
		String id = UUID.randomUUID().toString();
		BigDecimal amount1 = new BigDecimal(12345.1234);
		BigDecimal amount2 = new BigDecimal(23456.1234);
		Account account1 = new Account.Builder().id(id).amount(amount1).build();
		Account account2 = new Account.Builder().id(id).amount(amount2).build();
		assertFalse(account1.equals(account2));
	}

	@Test
	void hashCode1() {
		String id = UUID.randomUUID().toString();
		BigDecimal amount = new BigDecimal(12345.1234);
		int hash = Objects.hash(id, amount);
		assertEquals(hash, new Account.Builder().id(id).amount(amount).build().hashCode());
	}
}