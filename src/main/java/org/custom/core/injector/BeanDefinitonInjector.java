package org.custom.core.injector;

import static org.custom.core.utils.BeanDefUtil.getConfigBeanDefClasses;
import static org.custom.core.utils.BeanDefUtil.getConfigClassMethodsMap;
import static org.custom.core.utils.ClassUtil.FIRST_ELEMENT;
import static org.custom.core.utils.ClassUtil.ONE;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.custom.core.annotations.Default;
import org.custom.core.exceptions.DuplicateBeansFound;

public final class BeanDefinitonInjector extends Injector {

  private static final Logger LOGGER = Logger.getLogger("logger");

  @Override
  public void setContainerBeans(Map<Class<?>, Object> containerBeans) {
    this.containerBeans = containerBeans;
  }

  @Override
  public void setClassSet(List<Class<?>> classSet) {
    this.classSet = classSet;
  }


  @Override
  public void inject() {
    final var beanDefClasses = getConfigBeanDefClasses(classSet);
    final var configClassMethodsMap = getConfigClassMethodsMap(beanDefClasses, containerBeans);

    configClassMethodsMap.forEach((objectClassPair, methods) -> {
      Method method;
      if (methods.size() > ONE) {
        method = getDefaultAnnotatedMethod(methods).orElseThrow(
            () -> new DuplicateBeansFound(
                "No qualifying bean of type" + methods.get(FIRST_ELEMENT).getReturnType()
                    + " exists!")
        );
      } else {
        method = methods.get(FIRST_ELEMENT);
      }
      try {
        final var returnedBean = method.invoke(objectClassPair.left());
        this.containerBeans.put(returnedBean.getClass(), returnedBean);
      } catch (IllegalAccessException | InvocationTargetException e) {
        if (LOGGER.isLoggable(Level.SEVERE)) {
          LOGGER.log(Level.SEVERE, "An exception occurred " + e.getMessage());
        }
      }
    });
  }

  private Optional<Method> getDefaultAnnotatedMethod(List<Method> methods) {
    if (methods.stream().filter(method -> method.isAnnotationPresent(Default.class)).count()
        > ONE) {
      throw new DuplicateBeansFound("More than one default annotated method!");
    }
    return methods.stream().filter(method -> method.isAnnotationPresent(Default.class)).findFirst();
  }

}
