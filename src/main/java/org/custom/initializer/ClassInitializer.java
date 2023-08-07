package org.custom.initializer;

import static org.custom.utils.ClassUtil.ZERO;
import static org.custom.utils.ClassUtil.getConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import org.custom.checker.context.ContextChecker;
import org.custom.exceptions.NoSuchBeanFoundException;

public class ClassInitializer implements Initializer {

  private final List<Class<?>> context;
  private final List<ContextChecker> contextCheckers;

  public ClassInitializer(List<Class<?>> context, List<ContextChecker> contextCheckers) {
    this.context = context;
    this.contextCheckers = contextCheckers;
  }

  @Override
  public Object initialize(Class<?> clazz) {
    Object targetObject;
    var constructor = getConstructor(clazz);
    try {
      if (constructor.getParameterCount() == ZERO) {
        targetObject = constructor.newInstance();
      } else {
        targetObject = constructor.newInstance(
            Arrays.stream(constructor.getParameterTypes()).map(paramClass -> {
              if (isNotInContext(paramClass)) {
                throw new NoSuchBeanFoundException(
                    "Class need a bean of type " + paramClass
                        + " that could not be found in the context!");
              }
              return initialize(paramClass);
            }).toArray());
      }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e); // TODO
    }
    return targetObject;
  }

  private boolean isNotInContext(Class<?> paramClass) {
    var flag = true;
    for (ContextChecker checker : this.contextCheckers) {
      if (checker.isClassInContext(paramClass, this.context)) {
        flag = false;
        break;
      }
    }
    return flag;
  }
}