package pro.suslov.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.suslov.entity.Account;
import pro.suslov.service.AccountService;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AccountsResourceTest {

	@Mock
	private AccountService accountService;

	@InjectMocks
	private AccountsResource accountsResource;

	@Test
	public void createAccount() {
		Account account = new Account.Builder()
				.amount(new BigDecimal(0.01))
				.build();
		when(accountService.create(any())).thenAnswer(a -> a.getArgument(0));
		Response response = accountsResource.createAccount(account);
		Account result = (Account)response.getEntity();
		verify(accountService, times(1)).create(account);
		assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
		assertEquals(account.getId(), result.getId());
		assertEquals(account.getAmount(), result.getAmount());
	}

	@Test
	void getAccount() {
		String id = UUID.randomUUID().toString();
		Account account = new Account.Builder()
				.id(id)
				.amount(new BigDecimal(0.01))
				.build();
		when(accountService.getById(id)).thenReturn(account);
		Response response = accountsResource.getAccount(id);
		Account result = (Account)response.getEntity();
		verify(accountService, times(1)).getById(id);
		assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
		assertEquals(account.getId(), result.getId());
		assertEquals(account.getAmount(), result.getAmount());
	}

	@Test
	void getAccountNull() {
		String id = UUID.randomUUID().toString();
		Response response = accountsResource.getAccount(id);
		verify(accountService, times(1)).getById(any());
		assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
	}
}
