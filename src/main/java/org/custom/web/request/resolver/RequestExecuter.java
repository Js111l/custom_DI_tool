package org.custom.web.request.resolver;

import com.sun.net.httpserver.HttpExchange;
import java.lang.reflect.Method;
import java.util.List;
import org.custom.core.utils.pair.Pair;

public interface RequestExecuter {
  Object executeRequest(Pair<List<Method>, Object> handler, HttpExchange exchange,String httpMethod);

}
