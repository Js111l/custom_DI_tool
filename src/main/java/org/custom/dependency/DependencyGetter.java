package org.custom.dependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DependencyGetter {

  public Map<Class<?>, List<Class<?>>> getConstructorDependencies(List<Class<?>> classes) {
    Map<Class<?>, List<Class<?>>> classListMap = new HashMap<>();
    classes.forEach(cls -> {
      List<Class<?>> constructorDependencies = Arrays.stream(cls.getConstructors())
          .flatMap(constructor -> Arrays.stream(constructor.getParameterTypes()))
          .collect(Collectors.toList());
      classListMap.put(cls, constructorDependencies);
    });
    return classListMap;
  }
  @SuppressWarnings("unchecked")
  public Map<Class<?>, List<Field>> getFieldDependencies(List<Class<?>> classes,
      List<Class<?>> fieldAnnotations) {
    var fieldHashMap = new HashMap<Class<?>, List<Field>>();
    classes.forEach(cls -> {
      List<Field> fieldDependencies = Arrays.stream(cls.getDeclaredFields())
          .filter(field -> fieldAnnotations.stream().anyMatch(
              annotation -> field.isAnnotationPresent((Class<? extends Annotation>) annotation)))
          .collect(Collectors.toList());

      fieldHashMap.put(cls, fieldDependencies);
    });
    return fieldHashMap;
  }
}
