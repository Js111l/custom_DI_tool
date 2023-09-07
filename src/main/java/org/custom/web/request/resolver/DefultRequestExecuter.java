package org.custom.web.request.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.custom.core.utils.pair.Pair;
import org.custom.web.annotations.RequestBody;
import org.custom.web.factory.DefaultComponentsFactory;
import org.custom.web.valuegetter.ValueGetter;

public final class DefultRequestExecuter implements RequestExecuter {

  private static final Logger LOGGER = Logger.getLogger("exception logger");

  private final List<ValueGetter> valueGetters = new DefaultComponentsFactory().getValueGetters();

  private static Object deserializeObjectFromRequest(Class<?> type, HttpExchange exchange) {
    try (var requestBody = exchange.getRequestBody()) {
      final var mapper = new ObjectMapper();
      final var parser = mapper.createParser(requestBody);
      return parser.readValueAs(type);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "An exception occurred:", e);
      System.exit(1);
    }
    return null;
  }

  @Override
  public Object executeRequest(
      Pair<List<Method>, Object> handler, HttpExchange exchange, String httpMethod) {
    final var method = handler.left().get(0);
    final var params = new ArrayList<>();

    this.valueGetters.forEach(
        valueGetter -> {
          params.addAll(valueGetter.getValuesFromRequest(exchange, method));
        });

    Arrays.stream(method.getParameters())
        .forEach(
            x -> {
              if (x.isAnnotationPresent(RequestBody.class)) {
                params.add(deserializeObjectFromRequest(x.getType(), exchange));
              }
            });
    Object responseObject = null;
    try {
      if (method.getParameters().length == 0) {
        responseObject = method.invoke(handler.right(),
            params.toArray());
      }
      if (method.getParameters().length > 0) {
        responseObject = method.invoke(handler.right(),
            params.toArray());
      }
      return responseObject;
    } catch (IllegalAccessException | InvocationTargetException e) {
      LOGGER.log(Level.SEVERE, "An exception occurred:", e);
      System.exit(1);
      throw new RuntimeException(e);
    }
  }
}
