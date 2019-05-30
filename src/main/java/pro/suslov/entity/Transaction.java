package pro.suslov.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {
	private String from;
	private String to;
	private BigDecimal amount;

	public Transaction() {

	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		var that = (Transaction) o;
		return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(amount, that.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to, amount);
	}

	public static class Builder {
		private Transaction transaction;

		public Builder() {
			transaction = new Transaction();
		}

		public Transaction.Builder from(String from) {
			transaction.from = from;
			return this;
		}

		public Transaction.Builder to(String to) {
			transaction.to = to;
			return this;
		}

		public Transaction.Builder amount(BigDecimal amount) {
			transaction.amount = amount;
			return this;
		}

		public Transaction build() {
			return transaction;
		}
	}

}
