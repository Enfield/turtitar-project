package pro.suslov.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.suslov.entity.Transaction;
import pro.suslov.service.TransactionService;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionsResourceTest {

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private TransactionsResource transactionsResource;

	@Test
	public void createTransaction() {
		Transaction transaction = new Transaction.Builder()
				.from(UUID.randomUUID().toString())
				.to(UUID.randomUUID().toString())
				.amount(new BigDecimal(0.1234))
				.build();
		when(transactionService.create(transaction)).thenReturn(true);
		Response response = transactionsResource.createTransaction(transaction);
		verify(transactionService, times(1)).create(transaction);
		assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
	}
}