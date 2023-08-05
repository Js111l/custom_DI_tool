package org.custom.dependency;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.custom.annotations.BeanDef;
import org.custom.annotations.ConfigBeanDefinitions;

public class ConfigDependencyGetter implements DependencyGetter<Class<?>, Class<?>> {

  @Override
  public Map<Class<?>, List<Class<?>>> getDependencies(List<Class<?>> classes) {
    var map = new HashMap<Class<?>, List<Class<?>>>();
    classes.stream().filter(x -> x.isAnnotationPresent(ConfigBeanDefinitions.class))
        .forEach(configClass -> {
          List<Class<?>> methods = Arrays.stream(configClass.getMethods())
              .filter(method -> method.isAnnotationPresent(
                  BeanDef.class)).map(Method::getReturnType)
              .collect(Collectors.toList());

          map.put(configClass, methods);
        });
    return map;
  }
}
