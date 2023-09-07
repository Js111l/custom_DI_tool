package org.custom.web.handler;

import static org.custom.core.utils.ClassUtil.FIRST_ELEMENT;
import static org.custom.web.util.UrlUtil.hasMatchingUrl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.custom.core.utils.pair.Pair;
import org.custom.web.annotations.Delete;
import org.custom.web.annotations.Get;
import org.custom.web.annotations.Patch;
import org.custom.web.annotations.Post;
import org.custom.web.annotations.Put;
import org.custom.web.annotations.RequestUrl;
import org.custom.web.util.result.Result;

public final class HandlerResolver {

  private static final Logger logger = Logger.getLogger("exception logger");

  public static Result<Pair<List<Method>, Class<?>>, Throwable> getHandler( // geHandlerPair
      String requestMethod, String url, List<Class<?>> controllers) {
    Class<? extends Annotation> annotationCls = getCls(requestMethod);

    var handler =
        controllers.stream()
            .flatMap(
                controllerCls ->
                    Stream.of(
                        controllerFilter(
                            controllerCls.getMethods(), controllerCls, annotationCls, url)))
            .toList();

    if (handler.size() > 0) {
      var result = handler.stream().filter(x -> !x.left().isEmpty()).toList();
      if (result.size() > 1) {
        logger.log(Level.SEVERE, "More than one handler found!");
        System.exit(1);// TODO: 07.09.2023
      } else {
        return Result.of(() -> result.get(FIRST_ELEMENT));
      }
    }

    return Result.of(() -> handler.get(FIRST_ELEMENT));
  }

  private static Pair<List<Method>, Class<?>> controllerFilter(
      Method[] methods,
      Class<?> controllerCls,
      Class<? extends Annotation> annotationCls,
      String requestUrl) {

    var sb = new StringBuilder();
    if (controllerCls.isAnnotationPresent(RequestUrl.class)) {
      sb.append(controllerCls.getAnnotation(RequestUrl.class).url());
    }
    return Pair.of(
        Arrays.stream(methods)
            .filter(
                method ->
                    method.isAnnotationPresent(annotationCls)
                        && hasMatchingUrl(method, requestUrl, annotationCls, sb.toString()))
            .collect(Collectors.toList()),
        controllerCls);
  }

  private static Class<? extends Annotation> getCls(String requestMethod) {
    switch (requestMethod) {
      case "GET" -> {
        return Get.class;
      }
      case "POST" -> {
        return Post.class;
      }

      case "PUT" -> {
        return Put.class;
      }

      case "PATCH" -> {
        return Patch.class;
      }

      case "DELETE" -> {
        return Delete.class;
      }
      default -> {
        return null; // todo
      }
    }
  }
}
