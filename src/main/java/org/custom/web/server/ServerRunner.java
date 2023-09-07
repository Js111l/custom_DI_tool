package org.custom.web.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServerRunner {

  private static final Logger LOGGER = Logger.getLogger("logger");

  public static void runServer(HttpServer server, HttpHandler handler) {
    server.createContext("/", handler);
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.log(Level.INFO, "Initializing server");
    }
    server.start();
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.log(Level.INFO, "Server listening on port: " + server.getAddress().getPort());
    }
  }
}
