package org.custom.core.dependency;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.custom.core.annotations.Wired;

public class FieldDependencyGetter implements DependencyGetter<Class<?>, Field> {

  private List<Class<? extends Annotation>> fieldAnnotations;

  @Override
  public Map<Class<?>, List<Field>> getDependencies(List<Class<?>> classes) {

    var fieldHashMap = new HashMap<Class<?>, List<Field>>();
    classes.forEach(cls -> {
      List<Field> fieldDependencies = Arrays.stream(cls.getDeclaredFields())
          .filter(field -> field.isAnnotationPresent(Wired.class))
          .collect(Collectors.toList());

      fieldHashMap.put(cls, fieldDependencies);
    });
    return fieldHashMap;
  }
}
