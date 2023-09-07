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

  private final Logger logger = Logger.getLogger("logger");

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
        return targetObject;
      }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      logger.log(Level.SEVERE, "An exception occurred " + e.getMessage());
      System.exit(1);
    }
    return null;
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