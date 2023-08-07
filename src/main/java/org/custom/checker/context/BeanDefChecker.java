package org.custom.checker.context;

import java.util.Arrays;
import java.util.List;
import org.custom.annotations.BeanDef;
import org.custom.annotations.ConfigBeanDefinitions;

public class BeanDefChecker implements ContextChecker {
  @Override
  public boolean isClassInContext(Class<?> classToTest, List<Class<?>> context) {
    return context.stream().filter(x -> x.isAnnotationPresent(ConfigBeanDefinitions.class))
        .flatMap(x -> Arrays.stream(x.getMethods()))
        .filter(x -> x.isAnnotationPresent(BeanDef.class))
        .anyMatch(method -> method.getReturnType().equals(classToTest));
  }
}
