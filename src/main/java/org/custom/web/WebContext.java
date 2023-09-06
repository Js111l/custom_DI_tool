package org.custom.web;

import com.sun.net.httpserver.HttpServer;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.custom.web.annotations.RestController;
import org.custom.web.dispatcher.RequestDispatcher;
import org.custom.web.server.Server;
import org.custom.web.server.ServerRunner;

public final class WebContext {

  private HttpServer server;

  private List<Class<?>> controllers;
  private final Logger logger = Logger.getLogger("exception logger");

  public void configure(Map<Class<?>, Object> appContext, int port) {
    this.controllers =
        appContext.keySet().stream()
            .filter(x -> x.isAnnotationPresent(RestController.class))
            .collect(Collectors.toList());

    final var result = Server.getServer(port);

    result.onFailure(
        x -> {
          logger.log(Level.SEVERE, "An exception occurred: ", x.getMessage());
          System.exit(1);
        }); // TODO

    this.server = result.getOrNull();
  }

  public void initialize() {
    ServerRunner.runServer(this.server, new RequestDispatcher(this.controllers));
  }
}
