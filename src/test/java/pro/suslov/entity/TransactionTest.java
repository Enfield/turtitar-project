package pro.suslov.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

	@Test
	void getFrom() {
		String from = UUID.randomUUID().toString();
		Transaction transaction = new Transaction.Builder().from(from).build();
		assertEquals(from, transaction.getFrom());
	}

	@Test
	void setFrom() {
		String from = UUID.randomUUID().toString();
		Transaction transaction = new Transaction();
		transaction.setFrom(from);
		assertEquals(from, transaction.getFrom());
	}

	@Test
	void getTo() {
		String to = UUID.randomUUID().toString();
		Transaction transaction = new Transaction.Builder().to(to).build();
		assertEquals(to, transaction.getTo());
	}

	@Test
	void setTo() {
		String to = UUID.randomUUID().toString();
		Transaction transaction = new Transaction();
		transaction.setTo(to);
		assertEquals(to, transaction.getTo());
	}

	@Test
	void getAmount() {
		BigDecimal amount = new BigDecimal(12345.1234);
		Transaction transaction = new Transaction.Builder().amount(amount).build();
		assertEquals(amount, transaction.getAmount());
	}

	@Test
	void setAmount() {
		BigDecimal amount = new BigDecimal(12345.1234);
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		assertEquals(amount, transaction.getAmount());
	}

	@Test
	void equals() {
		String from = UUID.randomUUID().toString();
		String to = UUID.randomUUID().toString();
		BigDecimal amount = new BigDecimal(12345.1234);
		Transaction transaction1 = new Transaction.Builder().from(from).to(to).amount(amount).build();
		Transaction transaction2 = new Transaction.Builder().from(from).to(to).amount(amount).build();
		assertTrue(transaction1.equals(transaction2));
		assertFalse(transaction1.equals(null));
	}

	@Test
	void notEquals() {
		String from = UUID.randomUUID().toString();
		String to = UUID.randomUUID().toString();
		BigDecimal amount1 = new BigDecimal(12345.1234);
		BigDecimal amount2 = new BigDecimal(23456.1234);
		Transaction transaction1 = new Transaction.Builder().from(from).to(to).amount(amount1).build();
		Transaction transaction2 = new Transaction.Builder().from(from).to(to).amount(amount2).build();
		assertFalse(transaction1.equals(transaction2));
	}

	@Test
	void hashCode1() {
		String from = UUID.randomUUID().toString();
		String to = UUID.randomUUID().toString();
		BigDecimal amount = new BigDecimal(12345.1234);
		int hash = Objects.hash(from, to, amount);
		assertEquals(hash, new Transaction.Builder().from(from).to(to).amount(amount).build().hashCode());
	}
}