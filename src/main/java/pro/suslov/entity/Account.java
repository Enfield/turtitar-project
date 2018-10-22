package pro.suslov.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
	private String id;
	private BigDecimal amount;

	public Account() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		Account that = (Account) o;
		return Objects.equals(id, that.id) && Objects.equals(amount, that.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, amount);
	}

	public static class Builder {
		private Account account;

		public Builder() {
			account = new Account();
		}

		public Builder id(String id) {
			account.id = id;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			account.amount = amount;
			return this;
		}

		public Account build() {
			return account;
		}
	}
}
