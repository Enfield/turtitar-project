package pro.suslov.exceptions.handler;

import pro.suslov.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
	@Override
	public Response toResponse(ValidationException e) {
		return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type("text/plain").build();
	}
}