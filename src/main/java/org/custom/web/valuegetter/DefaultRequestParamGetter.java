package org.custom.web.valuegetter;

import static org.custom.web.util.UrlUtil.getHandlerUrl;

import com.sun.net.httpserver.HttpExchange;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import org.custom.web.annotations.RequestParam;
import org.custom.web.util.UrlUtil;
import org.custom.web.util.ValueCaster;

public final class DefaultRequestParamGetter extends ValueGetter {

  @Override
  public List<Object> getValuesFromRequest(HttpExchange exchange, Method handler) {
    final var urlFromRequest = exchange.getRequestURI().toString();
    final var urlFromHandler = getHandlerUrl(exchange.getRequestMethod(), handler);

    final var map = UrlUtil.getRequestParamValueMapFromUrl(urlFromRequest, urlFromHandler);

    final var params = new ArrayList<>();
    AtomicInteger counter = new AtomicInteger();

    try {
      Arrays.stream(handler.getParameters())
          .forEach(
              parameter -> {
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                  var value = map.get(parameter.getName());
                  counter.getAndIncrement();
                  if (value == null) {
                    throw new RuntimeException(
                        "Too many req params in request.");
                  }
                  params.add(ValueCaster.cast(value, parameter.getType()));
                }
              });
      if (counter.get() < map.keySet().size()) {
        throw new RuntimeException("Too many req params in method arguments.");
        // TODO: 06.09.2023
      }
    } catch (Exception e) {
      if (LOGGER.isLoggable(Level.SEVERE)) {
        LOGGER.log(Level.SEVERE, "An exception occurred " + e.getMessage());
      }
      System.exit(1);
    }
    return params;
  }
}
