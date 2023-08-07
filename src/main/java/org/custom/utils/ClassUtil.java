package org.custom.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassUtil {

  public static final int FIRST_ELEMENT = 0;
  public static final int ONE = 1;
  public static final int ZERO = 0;


  public static Constructor<?> getConstructor(Class<?> cls) {
    return Arrays.stream(cls.getConstructors()).findFirst().orElseThrow();
  }

  @SuppressWarnings("unchecked")
  public static List<Class<? extends Annotation>> getFieldAnnotations(
      List<Class<?>> scannedClasses) {
    return scannedClasses.stream()
        .filter(x -> x.isAnnotation() && x.isAnnotationPresent(Target.class)
            && x.getAnnotation(Target.class).value()[0] == ElementType.FIELD)
        .map(x -> (Class<? extends Annotation>) x)
        .collect(Collectors.toList());
  }
}
