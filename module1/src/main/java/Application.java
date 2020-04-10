import server.HTTPServer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        HTTPServer httpServer = new HTTPServer();
        httpServer.create();
    }
}
