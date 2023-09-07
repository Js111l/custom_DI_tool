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
  private final Logger logger = Logger.getLogger("exception logger");
  private final RequestExecuter resolver = new DefultRequestExecuter();

  public RequestDispatcher(List<Object> controllers) {
    this.controllers = controllers;
  }

  @Override
  public void handle(HttpExchange exchange) {
    var requestMethod = exchange.getRequestMethod();
    var url = exchange.getRequestURI().toString();

    // gets appropriate handler from Controllers
    var pairThrowableResult = getHandler(requestMethod, url, this.controllers);
    // set 404 response if error
    if (pairThrowableResult.isFailure() || leftInPairIsEmpty(pairThrowableResult)) {
      setResponse("", 404, exchange);
      return;
    }

    Object responseObject =
        executeRequest(exchange.getRequestMethod(), pairThrowableResult.getOrNull(), exchange);

    // response
    if (responseObject instanceof RestResponse restResponse) {
      var content = getJson(restResponse.body()); // TODO: 07.09.2023
      setResponse(content, restResponse.statusCode(), exchange);
      return;
    }
    // response
    var content = getJson(responseObject);
    setResponse(content, 200, exchange);
  }

  private Object executeRequest(
      String requestMethod, Pair<List<Method>, Object> pair, HttpExchange exchange) {
    switch (requestMethod) {
      case "GET" -> {
        return resolver.executeRequest(pair, exchange, "GET");
      }
      case "POST" -> {
        return resolver.executeRequest(pair, exchange, "POST");
      }
      case "PUT" -> {
        return resolver.executeRequest(pair, exchange, "PUT");
      }
      case "DELETE" -> {
        return resolver.executeRequest(pair, exchange, "DELETE");
      }
      case "PATCH" -> {
        return resolver.executeRequest(pair, exchange, "PATCH");
      }
      default -> {
        return null;
      }
    }
  }

  private boolean leftInPairIsEmpty(
      Result<Pair<List<Method>,Object>, Throwable> pairThrowableResult) {
    return pairThrowableResult.getOrNull().left().isEmpty();
  }

  private void setResponse(String content, int statusCode, HttpExchange exchange) {
    try {
      exchange.sendResponseHeaders(statusCode, content.getBytes().length);
      var responseBody = exchange.getResponseBody();
      responseBody.write(content.getBytes());
    } catch (IOException e) {
      logger.log(Level.SEVERE, "An exception occurred while sending response headers:", e);
      System.exit(1);
    }
  }

  private String getJson(Object responseObject) {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(responseObject);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "An exception occurred:", e);
      System.exit(1);
    }
    return "";
  }
}
