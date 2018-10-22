//package pro.suslov.integration;
//
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//import pro.suslov.Main;
//import pro.suslov.entity.Account;
//import pro.suslov.entity.Transaction;
//import pro.suslov.resource.AccountsResource;
//import pro.suslov.resource.TransactionsResource;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Response;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.ThreadLocalRandom;
//
//import static org.testng.AssertJUnit.assertEquals;
//
//
////TEST CASE
////	1) Add 10 accounts to system. Each with 1000 balance.
////	2) Check that the amount of funds in system is 10000
////	3) Transfer random amount between random accounts in high-concurrency environment
////	4) Check that the amount of all funds in system is still 10000
//public class ConcurrencyIT {
//
//	private static final int INVOCATION_COUNT = 1000;
//	private static HttpServer server;
//	private static WebTarget target;
//	private static List<String> accountsList = new ArrayList<>();
//
//	@BeforeClass
//	public static void setUp() throws Exception {
//		server = Main.startServer();
//		Client c = ClientBuilder.newClient();
//		target = c.target(Main.BASE_URI);
//		int i = 0;
//
//		//	1) Add 10 accounts to system. Each with 1000 balance.
//		while (i < 10) {
//			Account account = new Account.Builder()
//					.amount(new BigDecimal(1000))
//					.build();
//			Response response = target.path(AccountsResource.BASE_URL).request().post(Entity.json(account));
//			Account actual = response.readEntity(Account.class);
//			accountsList.add(actual.getId());
//			i++;
//		}
//		int result = 0;
//		for (String id : accountsList) {
//			Response actualResponse = target.path(String.format("/accounts/%s", id)).request().get();
//			Account actual = actualResponse.readEntity(Account.class);
//			result += actual.getAmount().intValue();
//		}
//		//	2) Check that the amount of funds in system is 10000
//		assertEquals(10000, result);
//	}
//
//	@AfterClass
//	public static void tearDownAndCheckResults() throws Exception {
//		int result = 0;
//		for (String id : accountsList) {
//			Response actualResponse = target.path(String.format("/accounts/%s", id)).request().get();
//			Account actual = actualResponse.readEntity(Account.class);
//			result += actual.getAmount().intValue();
//		}
//		//	4) Check that the amount of all funds in system is still 10000
//		assertEquals(10000, result);
//		server.shutdown();
//	}
//
//	@Test(threadPoolSize = 100, invocationCount = INVOCATION_COUNT)
//	public void testConcurrent() {
//		Random rand = new Random();
//		int randomAmount = rand.nextInt((1000 - 1) + 1) + 1;
//		String from = accountsList.get(ThreadLocalRandom.current().nextInt(accountsList.size()));
//		String to = accountsList.get(ThreadLocalRandom.current().nextInt(accountsList.size()));
//		accountsList.get(ThreadLocalRandom.current().nextInt(accountsList.size()));
//		Transaction transaction = new Transaction.Builder()
//				.from(from)
//				.to(to)
//				.amount(new BigDecimal(randomAmount))
//				.build();
//		//	3) Transfer random amount between random accounts in high-concurrency environment
//
//		target.path(TransactionsResource.BASE_URL).request().post(Entity.json(transaction));
//	}
//}
