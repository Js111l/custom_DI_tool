package org.custom.web.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServerRunner {

  private static final Logger logger = Logger.getLogger("logger"); // TODO: 07.09.2023  

  public static void runServer(HttpServer server, HttpHandler handler) {
    server.createContext("/", handler);
    logger.log(Level.INFO, "Initializing server");
    server.start();
    logger.log(Level.INFO,
        ("Server listening on port: " + server.getAddress().getPort()));
  }
}
