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
import org.custom.core.annotations.Default;
import org.custom.core.exceptions.DuplicateBeansFound;

public class BeanDefinitonInjector extends Injector {

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
    var beanDefClasses = getConfigBeanDefClasses(classSet);
    var configClassMethodsMap = getConfigClassMethodsMap(beanDefClasses, containerBeans);

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
        var returnedBean = method.invoke(objectClassPair.left());
        this.containerBeans.put(returnedBean.getClass(), returnedBean);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e); // TODO
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
