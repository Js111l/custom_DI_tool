package org.custom.injector;

import static org.custom.inspector.BeanDefInspector.getConfigBeanDefClasses;
import static org.custom.inspector.BeanDefInspector.getConfigClassMethodsMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.custom.annotations.Default;
import org.custom.exceptions.DuplicateBeansFound;

public class BeanDefinitonInjector extends Injector {

  private static final int ONE = 1;

  @Override
  public void setContainerBeans(Map<Class<?>, Object> containerBeans) {
    this.containerBeans = containerBeans;
  }

  @Override
  public void setClassSet(Set<Class<?>> classSet) {
    this.classSet = classSet;
  }


  @Override
  public void inject() {
    var beanDefClasses = getConfigBeanDefClasses(classSet);
    var configClassMethodsMap = getConfigClassMethodsMap(beanDefClasses, containerBeans);

    configClassMethodsMap.forEach((objectClassPair, methods) -> {
      Method method;
      if (methods.size() > ONE) {
        method = getDefaultAnnotatedMethod(methods).orElseThrow(
            () -> new DuplicateBeansFound(
                "No qualifying bean of type" + methods.get(0).getReturnType() + " exists!")
            //TODO
        );
      } else {
        method = methods.get(0);
      }
      try {
        var returnedBean = method.invoke(objectClassPair.getLeft());
        this.containerBeans.put(returnedBean.getClass(), returnedBean);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
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
