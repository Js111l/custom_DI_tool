package org.custom.inspector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyInspector {

  public void checkForCycles(Map<Class<?>, List<Class<?>>> classAllDependenciesMap) {
    var visited = new HashMap<Class<?>, Boolean>();
    classAllDependenciesMap.keySet()
        .forEach(aClass -> helper(aClass, visited, classAllDependenciesMap));
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

