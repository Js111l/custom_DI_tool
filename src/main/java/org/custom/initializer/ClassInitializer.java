package org.custom.initializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ClassInitializer implements Initializer {

  @Override
  public Object initialize(Constructor<?> constructor) {
    Object targetObject;
    try {
      if (constructor.getParameterCount() == 0) {
        targetObject = constructor.newInstance();
      } else {
        //TODO
        targetObject = constructor.newInstance(Arrays.stream(constructor.getParameterTypes())
            .map(x -> initialize(Arrays.stream(x.getConstructors()).findFirst().orElseThrow()))
            .toArray());
      }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return targetObject;
  }
}