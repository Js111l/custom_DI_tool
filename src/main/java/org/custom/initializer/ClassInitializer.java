package org.custom.initializer;

import static org.custom.Utils.ZERO;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class ClassInitializer implements Initializer {

  private final List<Class<?>> context;

  public ClassInitializer(List<Class<?>> context) {
    this.context = context;
  }

  @Override
  public Object initialize(Class<?> clazz) {
    Object targetObject;
    var constructor = Arrays.stream(clazz.getConstructors()).findFirst().orElseThrow();
    try {
      if (constructor.getParameterCount() == ZERO) {
        targetObject = constructor.newInstance();
      } else {
        targetObject = constructor.newInstance(
            Arrays.stream(constructor.getParameterTypes())
                .map(this::initialize)
                .toArray());
      }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return targetObject;
  }
}