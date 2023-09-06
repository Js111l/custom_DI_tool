package org.custom.web.initializer;

import static org.custom.core.utils.ClassUtil.ZERO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ClassInitializer {

  public Object initialize(Class<?> clazz) {
    Object targetObject;
    var constructor = getConstructor(clazz);
    try {
      if (constructor.getParameterCount() == ZERO) {
        targetObject = constructor.newInstance();
      } else {
        //              if (isNotInContext(paramClass)) {
        //                throw new NoSuchBeanFoundException(
        //                    "Class need a bean of type " + paramClass
        //                        + " that could not be found in the context!");
        //              }
        targetObject =
            constructor.newInstance(
                Arrays.stream(constructor.getParameterTypes()).map(this::initialize).toArray());
      }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e); // TODO
    }

    return targetObject;
  }

  public Constructor<?> getConstructor(Class<?> cls) {
    return Arrays.stream(cls.getConstructors()).findFirst().orElseThrow();
  }
}
