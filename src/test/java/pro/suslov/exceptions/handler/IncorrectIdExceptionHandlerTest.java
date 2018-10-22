package pro.suslov.exceptions.handler;

import org.junit.jupiter.api.Test;
import pro.suslov.exceptions.IncorrectIdException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncorrectIdExceptionHandlerTest {

	@Test
	void toResponse() {
		IncorrectIdException exception = new IncorrectIdException("IncorrectIdException");
		IncorrectIdExceptionHandler handler = new IncorrectIdExceptionHandler();
		Response response = handler.toResponse(exception);
		assertEquals(400, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN, response.getHeaderString("Content-Type"));
	}
}