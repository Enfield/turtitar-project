package pro.suslov.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.suslov.entity.Account;
import pro.suslov.service.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(AccountsResource.BASE_URL)
public class AccountsResource {
	public static final String BASE_URL = "/accounts";
	private static final Logger log = LoggerFactory.getLogger(AccountsResource.class);
	private AccountService accountService = new AccountService();

	@Path("{id}")
	@GET
	public Response getAccount(@PathParam("id") String id) {
		log.debug("getAccount is called for {id:%s}", id);
		Account account = accountService.getById(id);
		if (account == null) {
			return Response.ok().build();
		}
		return Response.ok(account).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAccount(Account account) {
		log.debug("createAccount is called for {amount:%s}", account.getAmount());
		Account createdAccount = accountService.create(account);
		return Response.ok(createdAccount).build();
	}

}
