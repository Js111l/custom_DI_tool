package org.custom.web.valuegetter;

import com.sun.net.httpserver.HttpExchange;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

public abstract class ValueGetter {
  protected static final Logger logger = Logger.getLogger("exception logger");
  public abstract List<Object> getValuesFromRequest(HttpExchange exchange, Method handler);
}
