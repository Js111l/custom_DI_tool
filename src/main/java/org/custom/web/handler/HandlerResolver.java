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

  private static final Logger LOGGER = Logger.getLogger("exception logger");

  private static final int ONE = 1;

  public static Result<Pair<List<Method>, Object>, Throwable> getHandler( // geHandlerPair
      String requestMethod, String url, List<Object> controllers) {
    final Class<? extends Annotation> annotationCls = getCls(requestMethod);

    final var handlers =
        controllers.stream()
            .flatMap(
                controller ->
                    Stream.of(
                        controllerFilter(
                            controller.getClass().getMethods(), controller, annotationCls, url)))
            .toList();

    if (!handlers.isEmpty()) {
      final var result = new ArrayList<>(
          handlers.stream().filter(x -> !x.left().isEmpty()).toList());
      if (result.size() > ONE) {
        LOGGER.log(Level.SEVERE, "More than one handler found!");
        System.exit(1);
      } else {
        return Result.of(() -> result.get(FIRST_ELEMENT));
      }
    }
    return Result.of(() -> handlers.get(FIRST_ELEMENT));
  }

  private static Pair<List<Method>, Object> controllerFilter(
      Method[] methods,
      Object controller,
      Class<? extends Annotation> annotationCls,
      String requestUrl) {

    var classRequestMapping = new StringBuilder();
    if (controller.getClass().isAnnotationPresent(RequestUrl.class)) {
      classRequestMapping.append(controller.getClass().getAnnotation(RequestUrl.class).url());
    }
    return Pair.of(
        Arrays.stream(methods)
            .filter(
                method ->
                    method.isAnnotationPresent(annotationCls)
                        && hasMatchingUrl(method, requestUrl, annotationCls,
                        classRequestMapping.toString()))
            .collect(Collectors.toList()), controller);
  }

  private static Class<? extends Annotation> getCls(String requestMethod) {
    Class<? extends Annotation> annotationClass = null;
    switch (requestMethod) {
      case "GET" -> annotationClass = Get.class;

      case "POST" -> annotationClass = Post.class;

      case "PUT" -> annotationClass = Put.class;

      case "PATCH" -> annotationClass = Patch.class;

      case "DELETE" -> annotationClass = Delete.class;

      default -> throw new IllegalStateException("Unexpected value: " + requestMethod);
    }
    return annotationClass;
  }
}
