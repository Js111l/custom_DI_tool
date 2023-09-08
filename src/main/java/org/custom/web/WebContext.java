package org.custom.web;

import com.sun.net.httpserver.HttpServer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.custom.web.annotations.RestController;
import org.custom.web.dispatcher.RequestDispatcher;
import org.custom.web.server.Server;
import org.custom.web.server.ServerRunner;

public final class WebContext {

  private HttpServer server;

  private List<Object> controllers;
  private static final Logger LOGGER = Logger.getLogger("exception logger");

  public void configure(Map<Class<?>, Object> appContext, int port) {
    this.controllers =
        appContext.entrySet().stream()
            .filter(x -> x.getKey().isAnnotationPresent(RestController.class))
            .map(Entry::getValue)
            .collect(Collectors.toList());

    final var result = Server.getServer(port);

    result.onFailure(
        throwable -> {
          if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "An exception occurred: " + throwable.getMessage());
            System.exit(1);
          }
        });

    this.server = result.getOrNull();
  }

  public void initialize() {
    ServerRunner.runServer(this.server, new RequestDispatcher(this.controllers));
  }
}
