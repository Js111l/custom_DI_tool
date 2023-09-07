package org.custom.core.dependency;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ConstructorDependencyGetter implements DependencyGetter<Class<?>, Class<?>> {

  @Override
  public Map<Class<?>, List<Class<?>>> getDependencies(List<Class<?>> classes) {
    final Map<Class<?>, List<Class<?>>> classListMap = new HashMap<>();
    classes.forEach(cls -> {
      final List<Class<?>> constructorDependencies = Arrays.stream(cls.getConstructors())
          .flatMap(constructor -> Arrays.stream(constructor.getParameterTypes()))
          .collect(Collectors.toList());
      classListMap.put(cls, constructorDependencies);
    });
    return classListMap;
  }
}
