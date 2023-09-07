package org.custom.web.util;

import java.lang.reflect.Method;
import java.util.Map;
import org.custom.web.util.result.Result;

public final class ValueMatcher {

  public static boolean valuesMatch(String requestUrl, String handlerUrl, Method method) {
    var map = UrlUtil.getPathVariableNameValueMapFromUrl(requestUrl, handlerUrl);
    var params = method.getParameters();

    if (containsPathVariables(map)) {
      for (int i = 0; i < params.length; i++) {
        if (!map.containsKey(params[i].getName())) {
          return false;
        } else if (isCastIsNotPossible(map.get(params[i].getName()), params[i].getType())) {
          return false;
        }
      }
    }

    return true;
  }

  private static boolean containsPathVariables(Map<String, String> map) {
    return map.size() > 0;
  }

  private static boolean isCastIsNotPossible(String value, Class<?> type) {
    return Result.of(() -> ValueCaster.cast(value, type)).isFailure();
  }
}
