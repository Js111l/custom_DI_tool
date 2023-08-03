package org.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

  public static HashMap<Class<?>, List<Class<?>>> mergeMaps(
      Map<Class<?>, List<Class<?>>> constructorDependencies,
      Map<Class<?>, List<Field>> fieldDependencies) {
    var mergedMap = new HashMap<>(constructorDependencies);
    fieldDependencies.forEach((key, value) ->
        mergedMap.put(key, value.stream().map(x -> x.getType()).collect(Collectors.toList())));
    return mergedMap;
  }

  public static Constructor<?> getConstructor(Class<?> cls) {
    var cons = Arrays.stream(cls.getConstructors()).findFirst().orElseThrow();
    return cons;//TODO
  }

  public static List<Class<?>> getFieldAnnotations(List<Class<?>> scannedClasses) {
    return scannedClasses.stream()
        .filter(x -> x.isAnnotation() && x.isAnnotationPresent(Target.class)
            && x.getAnnotation(Target.class).value()[0] == ElementType.FIELD)
        .collect(Collectors.toList());
  }
}
