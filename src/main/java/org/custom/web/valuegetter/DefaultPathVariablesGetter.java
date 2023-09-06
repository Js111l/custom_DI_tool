package org.custom.web.valuegetter;

import static org.custom.web.util.UrlUtil.deleteParams;
import static org.custom.web.util.UrlUtil.getHandlerUrl;

import com.sun.net.httpserver.HttpExchange;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import org.custom.web.annotations.PathVariable;
import org.custom.web.util.UrlUtil;
import org.custom.web.util.ValueCaster;

public final class DefaultPathVariablesGetter extends ValueGetter {

  @Override
  public List<Object> getValuesFromRequest(HttpExchange exchange, Method handler) {
    var urlFromRequest = exchange.getRequestURI().toString();
    var urlFromHandler = getHandlerUrl(exchange.getRequestMethod(), handler);

    var map = UrlUtil.getPathVarValuesFromUrl(deleteParams(urlFromRequest), urlFromHandler);

    var params = new ArrayList<>();
    AtomicInteger counter = new AtomicInteger();

    try {
      Arrays.stream(handler.getParameters())
          .forEach(
              parameter -> {
                if (parameter.isAnnotationPresent(PathVariable.class)) {
                  var value = map.get(parameter.getName());
                  counter.getAndIncrement();
                  if (value == null) {
                    throw new RuntimeException(
                        "Too many path variables in method arguments"); // TODO: 06.09.2023
                  }
                  params.add(ValueCaster.cast(value, parameter.getType()));
                }
              });
      if (counter.get() < map.keySet().size()) {
        throw new RuntimeException("Too many path variables in method arguments");
        // TODO: 06.09.2023
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      System.exit(1);
    }
    return params;
  }
}
