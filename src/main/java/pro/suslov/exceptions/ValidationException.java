package pro.suslov.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ValidationException extends WebApplicationException {
	public ValidationException(String message) {
		super(Response.status(Response.Status.BAD_REQUEST).
				entity(message).type("text/plain").build());
	}
}