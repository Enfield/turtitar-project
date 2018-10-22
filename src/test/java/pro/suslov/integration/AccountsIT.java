package pro.suslov.integration;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pro.suslov.Main;
import pro.suslov.entity.Account;
import pro.suslov.resource.AccountsResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AccountsIT {

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
	public void createAccount() {
		Account account = new Account.Builder()
				.amount(new BigDecimal(0.01))
				.build();
		Response response = target.path(AccountsResource.BASE_URL).request().post(Entity.json(account));
		Account actual = response.readEntity(Account.class);
		assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
		assertEquals(account.getAmount(), actual.getAmount());
		assertNotNull(actual.getId());
	}

	@Test
	public void createAccountNullAmount() {
		Account account = new Account.Builder()
				.amount(null)
				.build();
		Response response = target.path(AccountsResource.BASE_URL).request().post(Entity.json(account));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals(response.readEntity(String.class), "Amount cannot be null or negative");
	}

	@Test
	public void createAccountNegativeAmount() {
		Account account = new Account.Builder()
				.amount(new BigDecimal(-99.12345))
				.build();
		Response response = target.path(AccountsResource.BASE_URL).request().post(Entity.json(account));
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Amount cannot be null or negative", response.readEntity(String.class));
	}

	@Test
	public void getAccountWrongUUID() {
		Response response = target.path("/accounts/1").request().get();
		assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
		assertEquals("Incorrect account id", response.readEntity(String.class));
	}

	@Test
	public void getAccountCorrectUUIDEmptyResult() {
		Response response = target.path(String.format("/accounts/%s", UUID.randomUUID().toString())).request().get();
		assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
		assertEquals("", response.readEntity(String.class));
	}

	@Test
	public void getAccountCorrectUUID() {
		Response expectedResponse = target.path(AccountsResource.BASE_URL).request().post(Entity.json(new Account.Builder().amount(new BigDecimal(0.00001)).build()));
		Account expected = expectedResponse.readEntity(Account.class);
		Response actualResponse = target.path(String.format("/accounts/%s", expected.getId())).request().get();
		Account actual = actualResponse.readEntity(Account.class);
		assertEquals(Response.Status.OK, actualResponse.getStatusInfo().toEnum());
		assertEquals(expected.getId(), actual.getId());
	}
}
