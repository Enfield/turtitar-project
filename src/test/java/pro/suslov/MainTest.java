package pro.suslov;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void startServer() {
		HttpServer server = Main.startServer();
		server.shutdown();
	}
}