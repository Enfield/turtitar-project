package pro.suslov.integration;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pro.suslov.Main;
import pro.suslov.entity.Account;
import pro.suslov.entity.Transaction;
import pro.suslov.resource.AccountsResource;
import pro.suslov.resource.TransactionsResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TransactionsIT {
	private static HttpServer server;
	private static WebTarget target;

	@BeforeAll
	public static void setUp() throws Exception {
		server = Main.startServer();
		Client c = ClientBuilder.newClient();
		target = c.target(Main.BASE_URI);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		server.shutdown();
	}

	@Test
	public void createTransaction() {
		Response responseFrom = target.path(AccountsResource.BASE_URL).request().post(Entity.json(new Account.Builder()
				.amount(BigDecimal.valueOf(1000.1234))
				.build()));
		Account from = responseFrom.readEntity(Account.class);

		Response responseTo = target.path(AccountsResource.BASE_URL).request().post(Entity.json(new Account.Builder()
				.amount(BigDecimal.valueOf(1000.1234))
				.build()));
		Account to = responseTo.readEntity(Account.class);

		Transaction transaction = new Transaction.Builder()
				.from(from.getId())
				.to(to.getId())
				.amount(BigDecimal.valueOf(500.1234))
				.build();

		Response response = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(transaction));
		assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());

		Response resultResponseFrom = target.path(String.format("/accounts/%s", from.getId())).request().get();
		Account resultAccountFrom = resultResponseFrom.readEntity(Account.class);

		Response resultResponseTo = target.path(String.format("/accounts/%s", to.getId())).request().get();
		Account resultAccountTo = resultResponseTo.readEntity(Account.class);
		assertEquals("500.0000", resultAccountFrom.getAmount().toString());
		assertEquals("1500.2468", resultAccountTo.getAmount().toString());

		Transaction transaction1 = new Transaction.Builder()
				.from(to.getId())
				.to(from.getId())
				.amount(BigDecimal.valueOf(500.1234))
				.build();

		Response response1 = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(transaction1));
		assertEquals(Response.Status.OK, response1.getStatusInfo().toEnum());

		Response resultResponseTo1 = target.path(String.format("/accounts/%s", to.getId())).request().get();
		Account resultAccountTo1 = resultResponseTo1.readEntity(Account.class);
		Response resultResponseFrom1 = target.path(String.format("/accounts/%s", from.getId())).request().get();
		Account resultAccountFrom1 = resultResponseFrom1.readEntity(Account.class);

		assertEquals("1000.1234", resultAccountFrom1.getAmount().toString());
		assertEquals("1000.1234", resultAccountTo1.getAmount().toString());
	}

	@Test
	public void createTransactionNotEnoughMoney() {
		Response responseFrom = target.path(AccountsResource.BASE_URL).request().post(Entity.json(new Account.Builder()
				.amount(BigDecimal.valueOf(499.1234))
				.build()));
		Account from = responseFrom.readEntity(Account.class);

		Response responseTo = target.path(AccountsResource.BASE_URL).request().post(Entity.json(new Account.Builder()
				.amount(BigDecimal.valueOf(1000.1234))
				.build()));
		Account to = responseTo.readEntity(Account.class);

		Transaction transaction = new Transaction.Builder()
				.from(from.getId())
				.to(to.getId())
				.amount(BigDecimal.valueOf(500.1234))
				.build();

		Response response = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(transaction));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Not enough money in account {" + transaction.getFrom() + "} for transaction", response.readEntity(String.class));
	}

	@Test
	public void createTransactionNullFrom() {
		Transaction transaction = new Transaction.Builder()
				.to(UUID.randomUUID().toString())
				.amount(new BigDecimal(0.1234))
				.build();
		Response response = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(transaction));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Accounts ids are required", response.readEntity(String.class));
	}

	@Test
	public void createTransactionNullTo() {
		Transaction transaction = new Transaction.Builder()
				.from(UUID.randomUUID().toString())
				.amount(new BigDecimal(0.1234))
				.build();
		Response response = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(transaction));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Accounts ids are required", response.readEntity(String.class));
	}

	@Test
	public void createTransactionNullAmount() {
		Transaction Transaction = new Transaction.Builder()
				.build();
		Response response = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(Transaction));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Accounts ids are required", response.readEntity(String.class));
	}

	@Test
	public void createTransactionNegativeAmount() {
		Transaction Transaction = new Transaction.Builder()
				.from(UUID.randomUUID().toString())
				.to(UUID.randomUUID().toString())
				.amount(new BigDecimal(-99.12345))
				.build();
		Response response = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(Transaction));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Amount cannot be null or negative", response.readEntity(String.class));
	}

	@Test
	public void createTransactionSameAmount() {
		String id = UUID.randomUUID().toString();
		Transaction Transaction = new Transaction.Builder()
				.from(id)
				.to(id)
				.amount(new BigDecimal(-99.12345))
				.build();
		Response response = target.path(TransactionsResource.BASE_URL).request().post(Entity.json(Transaction));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Accounts ids pass to the same account", response.readEntity(String.class));
	}
}
