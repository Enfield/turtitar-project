package pro.suslov.exceptions.handler;

import org.junit.jupiter.api.Test;
import pro.suslov.exceptions.ValidationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationExceptionHandlerTest {

	@Test
	void toResponse() {
		ValidationException exception = new ValidationException("ValidationException");
		ValidationExceptionHandler handler = new ValidationExceptionHandler();
		Response response = handler.toResponse(exception);
		assertEquals(400, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN, response.getHeaderString("Content-Type"));
	}
}