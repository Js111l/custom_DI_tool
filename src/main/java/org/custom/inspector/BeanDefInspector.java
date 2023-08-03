package org.custom.inspector;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.custom.annotations.BeanDef;
import org.custom.annotations.ConfigBeanDefinitions;

public class BeanDefInspector {

  public static HashMap<Object, List<Method>> getConfigClassMethodsMap(List<Class<?>> beanDefClasses,
      Map<Class<?>, Object> containerBeans) {
    var classListHashMap = new HashMap<Object, List<Method>>();

      beanDefClasses.forEach(configClass -> {
      var obj = containerBeans.get(configClass);
      var methods = getAllAnnotatedMethodsFromObject(obj);

      methods.forEach(method -> {
        addToMap(classListHashMap, method, obj);
      });
    });
    return classListHashMap;
  }

  public static List<Class<?>> getConfigBeanDefClasses(Set<Class<?>> classSet) {
    return classSet.stream().filter(x -> x.isAnnotationPresent(ConfigBeanDefinitions.class))
        .collect(
            Collectors.toList());

  }

  public static void addToMap(HashMap<Object, List<Method>> classListHashMap,
      Method method, Object obj) {
    var list = classListHashMap.getOrDefault(obj, new ArrayList<>());
    list.add(method);
    classListHashMap.put(obj, list);
  }

  public static List<Method> getAllAnnotatedMethodsFromObject(Object obj) {
    return Arrays.stream(obj.getClass().getMethods())
        .filter(method -> method.isAnnotationPresent(
            BeanDef.class)).collect(Collectors.toList());
  }
}
