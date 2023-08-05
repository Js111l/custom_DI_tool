package org.custom.initializer;

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
//    if (!context.contains(clazz)) {
//      throw new RuntimeException("lalalaland");
//    } //TODO
    Object targetObject;
    var constructor = Arrays.stream(clazz.getConstructors()).findFirst().orElseThrow();//TODO
    try {
      if (constructor.getParameterCount() == 0) {
        targetObject = constructor.newInstance();
      } else {
        targetObject = constructor.newInstance(
            Arrays.stream(constructor.getParameterTypes())   //TODO
                .map(this::initialize)
                .toArray());
      }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return targetObject;
  }
}