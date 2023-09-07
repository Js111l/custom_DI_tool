package org.custom.web.dispatcher;

import static org.custom.web.handler.HandlerResolver.getHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.custom.core.utils.pair.Pair;
import org.custom.web.model.RestResponse;
import org.custom.web.request.resolver.DefultRequestExecuter;
import org.custom.web.request.resolver.RequestExecuter;
import org.custom.web.util.result.Result;

public class RequestDispatcher implements HttpHandler {

  private final List<Object> controllers;
  private static final Logger LOGGER = Logger.getLogger("exception logger");
  private final RequestExecuter resolver = new DefultRequestExecuter();

  public RequestDispatcher(List<Object> controllers) {
    this.controllers = controllers;
  }

  @Override
  public void handle(HttpExchange exchange) {
    final var requestMethod = exchange.getRequestMethod();
    final var url = exchange.getRequestURI().toString();

    // gets appropriate handler from Controllers
    final var pairThrowableResult = getHandler(requestMethod, url, this.controllers);

    // set 404 response if error
    if (pairThrowableResult.isFailure() || leftInPairIsEmpty(pairThrowableResult)) {
      setResponse("", 404, exchange);
      return;
    }

    final Object responseObject = executeRequest(exchange.getRequestMethod(),
        pairThrowableResult.getOrNull(), exchange);

    // rest response
    if (responseObject instanceof RestResponse restResponse) {
      final var content = getJson(restResponse.body()); // TODO: 07.09.2023
      setResponse(content, restResponse.statusCode(), exchange);
      return;
    }

    // response
    final var content = getJson(responseObject);
    setResponse(content, 200, exchange);
  }

  private Object executeRequest(String requestMethod, Pair<List<Method>, Object> pair,
      HttpExchange exchange) {
    Object response = null;
    switch (requestMethod) {
      case "GET" -> response = resolver.executeRequest(pair, exchange, "GET");

      case "POST" -> response = resolver.executeRequest(pair, exchange, "POST");

      case "PUT" -> response = resolver.executeRequest(pair, exchange, "PUT");

      case "DELETE" -> response = resolver.executeRequest(pair, exchange, "DELETE");

      case "PATCH" -> response = resolver.executeRequest(pair, exchange, "PATCH");

      default -> throw new IllegalStateException("Unexpected value: " + requestMethod);
    }
    return response;
  }

  private boolean leftInPairIsEmpty(
      Result<Pair<List<Method>, Object>, Throwable> pairThrowableResult) {
    return pairThrowableResult.getOrNull().left().isEmpty();
  }

  private void setResponse(String content, int statusCode, HttpExchange exchange) {
    try {
      exchange.sendResponseHeaders(statusCode, content.getBytes().length);
      final var responseBody = exchange.getResponseBody();
      responseBody.write(content.getBytes());
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "An exception occurred while sending response headers:", e);
      System.exit(1);
    }
  }

  private String getJson(Object responseObject) {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(responseObject);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "An exception occurred:", e);
      System.exit(1);
      throw new RuntimeException(e);
    }
  }
}
