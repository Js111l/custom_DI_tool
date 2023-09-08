package org.custom.web.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import org.custom.web.util.result.Result;

public final class ValueMatcher {

  public static boolean valuesMatch(String requestUrl, String handlerUrl, Method method) {
    final var map = UrlUtil.getPathVariableNameValueMapFromUrl(requestUrl, handlerUrl);
    final var params = method.getParameters();

    if (containsPathVariables(map)) {
      for (Parameter param : params) {
        if (map.containsKey(param.getName())) {
          if (isCastIsNotPossible(map.get(param.getName()), param.getType())) {
            return false;
          }
        } else {
          return false;
        }
      }
    }

    return true;
  }

  private static boolean containsPathVariables(Map<String, String> map) {
    return !map.isEmpty();
  }

  private static boolean isCastIsNotPossible(String value, Class<?> type) {
    return Result.of(() -> ValueCaster.cast(value, type)).isFailure();
  }
}
