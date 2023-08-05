package org.custom.dependency;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConstructorDependencyGetter implements DependencyGetter<Class<?>, Class<?>> {

  @Override
  public Map<Class<?>, List<Class<?>>> getDependencies(List<Class<?>> classes) {
    Map<Class<?>, List<Class<?>>> classListMap = new HashMap<>();
    classes.forEach(cls -> {
      List<Class<?>> constructorDependencies = Arrays.stream(cls.getConstructors())
          .flatMap(constructor -> Arrays.stream(constructor.getParameterTypes()))
          .collect(Collectors.toList());
      classListMap.put(cls, constructorDependencies);
    });
    return classListMap;
  }
}
