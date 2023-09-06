package org.custom.core.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;
import org.custom.core.utils.pair.Pair;

public class BeanDefUtil {

  public static Map<Pair<Object, Class<?>>, List<Method>> getConfigClassMethodsMap(
      List<Class<?>> beanDefClasses,
      Map<Class<?>, Object> containerBeans) {
    var classListHashMap = new HashMap<Pair<Object, Class<?>>, List<Method>>();
    beanDefClasses.forEach(configClass -> {
      var configObj = containerBeans.get(configClass);
      var methods = getAllAnnotatedMethodsFromObject(configObj);

      methods.forEach(
          method -> addToMap(classListHashMap, method, configObj, method.getReturnType()));
    });
    return classListHashMap;
  }

  public static List<Class<?>> getConfigBeanDefClasses(List<Class<?>> classSet) {
    return classSet.stream().filter(x -> x.isAnnotationPresent(ConfigBeanDefinitions.class))
        .collect(
            Collectors.toList());

  }

  private static void addToMap(Map<Pair<Object, Class<?>>, List<Method>> classListHashMap,
      Method method, Object configObj, Class<?> clazz) {
    var list = classListHashMap.getOrDefault(Pair.of(configObj, clazz), new ArrayList<>());
    list.add(method);
    classListHashMap.put(Pair.of(configObj, clazz), list);
  }

  public static List<Method> getAllAnnotatedMethodsFromObject(Object obj) {
    return Arrays.stream(obj.getClass().getMethods())
        .filter(method -> method.isAnnotationPresent(
            BeanDef.class)).collect(Collectors.toList());
  }
}
