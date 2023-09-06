package org.custom.web.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
public final class ServerRunner {
  public static void runServer(HttpServer server, HttpHandler handler) {
    server.createContext("/", handler);
    System.out.println("Initializing server");
    server.start();
    System.out.println("Server listening on port: " + server.getAddress().getPort());
  }
}
