package org.custom.inspector;

import com.google.common.collect.ArrayListMultimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.custom.annotations.Default;
import org.custom.exceptions.DuplicateBeansFound;

public class DependencyInspector {

  public void checkForCycles(Map<Class<?>, List<Class<?>>> classAllDependenciesMap) {
    var visited = new HashMap<Class<?>, Boolean>();
    classAllDependenciesMap.keySet()
        .forEach(aClass -> helper(aClass, visited, classAllDependenciesMap));
  }

  public void checkForDuplicates(Map<Class<?>, List<Class<?>>> classAllDependenciesMap) {
    var duplicates = ArrayListMultimap.<Class<?>, Class<?>>create();
    classAllDependenciesMap.values().stream().flatMap(Collection::stream).forEach(
        cls -> {
          if (classAllDependenciesMap.containsKey(cls)) {
            duplicates.put(cls, cls);
          }
        });
    duplicates.keySet().forEach(
        key -> duplicates.get(key).stream().filter(x -> x.isAnnotationPresent(Default.class))
            .findFirst().orElseThrow(
                () -> new DuplicateBeansFound(
                    "No qualifying bean of type" + key.getName() + " exists!")
            ));
    // throw new DuplicateBeansFound("No qualifying bean of type exists!");
//Add duplicates to some set and then filter this out and check if one of them have default annotation
    // or not. If not, throw the exception. TODO

  }

  private void helper(Class<?> aClass, Map<Class<?>, Boolean> visited,
      Map<Class<?>, List<Class<?>>> classListMap) {
    var list = classListMap.getOrDefault(aClass, new ArrayList<>());

    for (int i = 0; i < list.size(); i++) {
      if (visited.get(aClass) == null || !visited.get(aClass)) {
        visited.put(aClass, true);
        list.forEach(x -> helper(x, visited, classListMap));
        visited.put(aClass, false);
      } else {
        throw new RuntimeException("Cycle!");
      }
    }
  }
}

