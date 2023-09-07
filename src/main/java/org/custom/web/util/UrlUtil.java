package org.custom.web.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.custom.web.annotations.Delete;
import org.custom.web.annotations.Get;
import org.custom.web.annotations.Patch;
import org.custom.web.annotations.Post;
import org.custom.web.annotations.Put;

public class UrlUtil {

  public static Map<String, String> getPathVariableNameValueMapFromUrl(
      String requestUrl, String handlerUrl) {
    final var map = new HashMap<String, String>();
    if (isMatching(requestUrl, handlerUrl)) {
      int i = 0;
      int j = 0;
      while (i < requestUrl.length() && j < handlerUrl.length()) {
        var handle = new StringBuilder(handlerUrl);
        handle.append(' ');
        var varName = new StringBuilder();
        var varValue = new StringBuilder();
        var lastChar = ' ';

        if (handlerUrl.charAt(j) == '{') {
          j++;
          while (handlerUrl.charAt(j) != '}') {
            varName.append(handlerUrl.charAt(j));
            j++;
          }

          lastChar = handle.charAt(j + 1);
          while (i < requestUrl.length() && requestUrl.charAt(i) != lastChar) {
            varValue.append(requestUrl.charAt(i));
            i++;
          }
          i--;

          map.put(varName.toString(), varValue.toString());
          continue;
        }
        j++;
        i++;
      }
    }
    return map;
  }

  public static Map<String, String> getRequestParamValueMapFromUrl(
      String requestUrl, String handlerUrl) {
    var map = new HashMap<String, String>();
    if (isMatchingReqParam(requestUrl, handlerUrl)) {
      int i = 0;
      while (i < requestUrl.length()) {
        if (requestUrl.charAt(i) == '?' || requestUrl.charAt(i) == '&') {
          int j = i + 1;
          var name = new StringBuilder();
          var value = new StringBuilder();
          while (j < requestUrl.length()) {
            if (requestUrl.charAt(j) == '=') {
              j++;
              appendValue(value, j, requestUrl);
              break;
            }
            name.append(requestUrl.charAt(j));
            j++;
          }
          map.put(name.toString(), value.toString());
        }
        i++;
      }
    }
    return map;
  }

  public static boolean isMatchingReqParam(String requestUrl, String handlerUrl, Method method) {
    if (ValueMatcher.valuesMatch(requestUrl, handlerUrl, method)) {
      var result = deleteRequestParams(requestUrl);
      return isMatching(result, handlerUrl);
    } else {
      return false;
    }
  }

  public static boolean isMatchingReqParam(String requestUrl, String handlerUrl) {
    var result = deleteRequestParams(requestUrl);
    return isMatching(result, handlerUrl);
  }

  public static String deleteRequestParams(String requestUrl) {
    int i = 0;

    var updatedRequestUrl = new StringBuilder();
    boolean flag = true;
    boolean passedEqualSign = false;

    while (i < requestUrl.length()) {
      if (requestUrl.charAt(i) == '?') {
        flag = false;
      }
      if (requestUrl.charAt(i) == '=' && flag) {
        passedEqualSign = true;
      }
      if (passedEqualSign && requestUrl.charAt(i) == '/') {
        flag = true;
        passedEqualSign = false;
        i++;
        continue;
      }
      if (passedEqualSign && requestUrl.charAt(i) == '?') {
        flag = false;
        passedEqualSign = false;
        i++;
        continue;
      }
      if (flag) {
        updatedRequestUrl.append(requestUrl.charAt(i));
      }
      i++;
    }
    //    if (sb.charAt(sb.length() - 1) == '/') {
    //      sb.deleteCharAt(sb.length() - 1); // TODO: 06.09.2023 /
    //    }
    return updatedRequestUrl.toString();
  }

  private static void appendValue(StringBuilder value, int index, String requestUrl) {
    var j = index;
    while (j < requestUrl.length()) {
      if (requestUrl.charAt(j) == '/' || requestUrl.charAt(j) == '&') {
        break;
      }
      value.append(requestUrl.charAt(j));
      j++;
    }
  }

  public static boolean isMatching(String requestUrl, String handlerUrl, Method method) {
    if (ValueMatcher.valuesMatch(requestUrl, handlerUrl, method)) {
      int j = 0;
      int i = 0;

      int slashCounterX = 0;
      int slashCounterY = 0;

      var lastChar = ' ';

      var handlerUrlStringBuilder = new StringBuilder(handlerUrl);
      handlerUrlStringBuilder.append(' ');

      while (i < requestUrl.length() && j < handlerUrlStringBuilder.length()) {
        if (handlerUrlStringBuilder.charAt(j) == '{') {
          while (j < handlerUrlStringBuilder.length() && handlerUrlStringBuilder.charAt(j) != '}') {
            j++;
          }
          j++;
          lastChar = handlerUrlStringBuilder.charAt(j);
          while (i < requestUrl.length() && requestUrl.charAt(i) != lastChar) {
            if (i + 1 == requestUrl.length() && j + 1 == handlerUrlStringBuilder.length()) {
              return slashCounterY == slashCounterX;
            }
            if (requestUrl.charAt(i) == '/') {
              slashCounterX++;
            }
            i++;
          }
        }
        if ((i >= requestUrl.length() && j < handlerUrlStringBuilder.length()
            || i < requestUrl.length() && j >= handlerUrlStringBuilder.length())
            || requestUrl.charAt(i) != handlerUrlStringBuilder.charAt(j)) {
          return false;
        }
        if (requestUrl.charAt(i) == '/') {
          slashCounterX++;
        }
        if (handlerUrlStringBuilder.charAt(j) == '/') {
          slashCounterY++;
        }
        i++;
        j++;
      }

      return handlerUrlStringBuilder.substring(j).trim().equals(requestUrl.substring(i));
    }
    return false; // TODO: 07.09.2023 or exception ?  
  }

  public static boolean isMatching(String requestUrl, String handlerUrl) {
    int j = 0;
    int i = 0;

    int slashCounterX = 0;
    int slashCounterY = 0;

    var lastChar = ' ';
    var handlerUrlStringBuilder = new StringBuilder(handlerUrl);

    handlerUrlStringBuilder.append(' ');
    while (i < requestUrl.length() && j < handlerUrlStringBuilder.length()) {
      if (handlerUrlStringBuilder.charAt(j) == '{') {
        while (j < handlerUrlStringBuilder.length() && handlerUrlStringBuilder.charAt(j) != '}') {
          j++;
        }
        j++;
        lastChar = handlerUrlStringBuilder.charAt(j);
        while (i < requestUrl.length() && requestUrl.charAt(i) != lastChar) {
          if (i + 1 == requestUrl.length() && j + 1 == handlerUrlStringBuilder.length()) {
            return slashCounterY == slashCounterX;
          }
          if (requestUrl.charAt(i) == '/') {
            slashCounterX++;
          }
          i++;
        }
      }
      if ((i >= requestUrl.length() && j < handlerUrlStringBuilder.length()
          || i < requestUrl.length() && j >= handlerUrlStringBuilder.length())
          || requestUrl.charAt(i) != handlerUrlStringBuilder.charAt(j)) {

        return false;
      }
      if (requestUrl.charAt(i) == '/') {
        slashCounterX++;
      }
      if (handlerUrlStringBuilder.charAt(j) == '/') {
        slashCounterY++;
      }
      i++;
      j++;
    }

    return handlerUrlStringBuilder.substring(j).trim().equals(requestUrl.substring(i));
  }

  public static boolean hasMatchingUrl(
      Method method,
      String requestUrl,
      Class<? extends Annotation> annotationCls,
      String sb) {
    boolean result;
    switch (annotationCls.getSimpleName()) {
      case "Get" -> result = isMatching(requestUrl, sb + getHandlerUrl("GET", method), method)
          || isMatchingReqParam(requestUrl,
          sb + getHandlerUrl("GET", method), method);

      case "Post" -> result = isMatching(requestUrl, sb + getHandlerUrl("POST", method))
          || isMatchingReqParam(requestUrl,
          sb + getHandlerUrl("POST", method));

      case "Put" -> result = isMatching(requestUrl, sb + getHandlerUrl("PUT", method))
          || isMatchingReqParam(requestUrl,
          sb + getHandlerUrl("PUT", method));

      case "Patch" -> result = isMatching(requestUrl, sb + getHandlerUrl("PATCH", method))
          || isMatchingReqParam(requestUrl,
          sb + getHandlerUrl("PATCH", method));

      case "Delete" -> result = isMatching(requestUrl, sb + getHandlerUrl("DELETE", method))
          || isMatchingReqParam(requestUrl,
          sb + getHandlerUrl("DELETE", method));

      default ->
          throw new IllegalStateException("Unexpected value: " + annotationCls.getSimpleName());
    }
    return result;
  }

  public static String getHandlerUrl(String requestMethod, Method handler) {
    var url = "";
    switch (requestMethod) {
      case "GET" -> url = handler.getAnnotation(Get.class).url();

      case "POST" -> url = handler.getAnnotation(Post.class).url();

      case "PUT" -> url = handler.getAnnotation(Put.class).url();

      case "PATCH" -> url = handler.getAnnotation(Patch.class).url();

      case "DELETE" -> url = handler.getAnnotation(Delete.class).url();

      default -> throw new IllegalStateException("Unexpected value: " + requestMethod);
    }
    return url;
  }
}
