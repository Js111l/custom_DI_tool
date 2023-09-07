package org.custom.core.dependency;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;

public final class ConfigDependencyGetter implements DependencyGetter<Class<?>, Class<?>> {

  @Override
  public Map<Class<?>, List<Class<?>>> getDependencies(List<Class<?>> classes) {
    final var map = new HashMap<Class<?>, List<Class<?>>>();
    classes.stream().filter(x -> x.isAnnotationPresent(ConfigBeanDefinitions.class))
        .forEach(configClass -> {
          final List<Class<?>> methods = Arrays.stream(configClass.getMethods())
              .filter(method -> method.isAnnotationPresent(
                  BeanDef.class)).map(Method::getReturnType)
              .collect(Collectors.toList());
          map.put(configClass, methods);
        });

    return map;
  }
}
