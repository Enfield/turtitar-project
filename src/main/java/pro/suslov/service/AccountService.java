package pro.suslov.service;

import pro.suslov.entity.Account;
import pro.suslov.exceptions.IncorrectIdException;
import pro.suslov.exceptions.ValidationException;
import pro.suslov.repository.AccountRepository;

import java.util.UUID;

public class AccountService {

	private AccountRepository accountRepository = new AccountRepository();

	public Account create(Account account) throws ValidationException {
		validate(account);
		return accountRepository.insert(account);
	}


	public Account getById(String id) throws IllegalArgumentException {
		try {
			String uuid = UUID.fromString(id).toString();
			return accountRepository.find(uuid);
		} catch (IllegalArgumentException exception) {
			throw new IncorrectIdException("Incorrect account id");
		}
	}

	private void validate(Account account) throws ValidationException {
		if (account.getAmount() == null || account.getAmount().signum() == -1) {
			throw new ValidationException("Amount cannot be null or negative");
		}
	}
}
