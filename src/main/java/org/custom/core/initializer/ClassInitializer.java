package org.custom.core.initializer;

import static org.custom.core.utils.ClassUtil.ZERO;
import static org.custom.core.utils.ClassUtil.getConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.custom.core.checker.context.ContextChecker;
import org.custom.core.exceptions.NoSuchBeanFoundException;

public class ClassInitializer implements Initializer {

  private final List<Class<?>> context;
  private final List<ContextChecker> contextCheckers;

  private static final Logger LOGGER = Logger.getLogger("logger");

  public ClassInitializer(List<Class<?>> context, List<ContextChecker> contextCheckers) {
    this.context = context;
    this.contextCheckers = contextCheckers;
  }

  @Override
  public Object initialize(Class<?> clazz) {
    final var constructor = getConstructor(clazz);
    Object initializedObject;
    try {
      if (constructor.getParameterCount() == ZERO) {
        initializedObject = constructor.newInstance();
      } else {
        initializedObject = constructor.newInstance(
            Arrays.stream(constructor.getParameterTypes()).map(paramClass -> {
              if (isNotInContext(paramClass)) {
                throw new NoSuchBeanFoundException(
                    "Class need a bean of type " + paramClass
                        + " that could not be found in the context!");
              }
              return initialize(paramClass);
            }).toArray());
      }
      return initializedObject;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      if (LOGGER.isLoggable(Level.SEVERE)) {
        LOGGER.log(Level.SEVERE, "An exception occurred " + e.getMessage());
      }
      System.exit(1);
      throw new RuntimeException(e);
    }
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