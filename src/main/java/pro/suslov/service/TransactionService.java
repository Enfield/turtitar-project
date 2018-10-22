package pro.suslov.service;

import pro.suslov.entity.Transaction;
import pro.suslov.exceptions.ValidationException;
import pro.suslov.repository.TransactionRepository;

import java.util.UUID;

public class TransactionService {

	private TransactionRepository transactionRepository = new TransactionRepository();

	public boolean create(Transaction transaction) throws ValidationException {
		validate(transaction);
		return transactionRepository.insert(transaction);
	}

	private void validate(Transaction transaction) throws ValidationException {
		try {
			if (transaction.getFrom() == null || transaction.getTo() == null) {
				throw new ValidationException("Accounts ids are required");
			}
			UUID from = UUID.fromString(transaction.getFrom());
			UUID to = UUID.fromString(transaction.getTo());
			if (from.equals(to)) {
				throw new ValidationException("Accounts ids pass to the same account");
			}
			if (transaction.getAmount() == null || transaction.getAmount().signum() == -1) {
				throw new ValidationException("Amount cannot be null or negative");
			}
		} catch (IllegalArgumentException exception) {
			throw new ValidationException("Incorrect 'from'/'to' account id");
		}
	}
}
