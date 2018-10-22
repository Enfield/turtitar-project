package pro.suslov.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.suslov.entity.Transaction;
import pro.suslov.service.TransactionService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(TransactionsResource.BASE_URL)
public class TransactionsResource {
	public static final String BASE_URL = "/transactions";
	private static final Logger log = LoggerFactory.getLogger(AccountsResource.class);
	private TransactionService transactionService = new TransactionService();


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTransaction(Transaction transaction) {
		log.debug("createTransaction is called for {from: %s, to: %s, amount:%s}", transaction.getFrom(), transaction.getTo(), transaction.getAmount());
		transactionService.create(transaction);
		return Response.ok().build();
	}
}
