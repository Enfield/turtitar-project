package pro.suslov.exceptions.handler;

import pro.suslov.exceptions.IncorrectIdException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IncorrectIdExceptionHandler implements ExceptionMapper<IncorrectIdException> {
	@Override
	public Response toResponse(IncorrectIdException e) {
		return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type("text/plain").build();
	}
}