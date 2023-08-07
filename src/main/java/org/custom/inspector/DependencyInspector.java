package org.custom.inspector;

import com.google.common.collect.ArrayListMultimap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.custom.annotations.Default;
import org.custom.checker.duplicate.DuplicateChecker;
import org.custom.exceptions.CilcularDependencyFoundExcpetion;
import org.custom.exceptions.DuplicateBeansFound;

public class DependencyInspector {

  public void checkForCycles(Map<Class<?>, List<Class<?>>> classAllDependenciesMap) {
    var visited = new HashMap<Class<?>, Boolean>();
    classAllDependenciesMap.keySet()
        .forEach(aClass -> helper(aClass, visited, classAllDependenciesMap));
  }

  public void checkForDuplicates(Map<Class<?>, List<Class<?>>> classAllDependenciesMap,
      List<DuplicateChecker> duplicateCheckers) {
    var duplicates = ArrayListMultimap.<Class<?>, Class<?>>create();

    duplicateCheckers.forEach(duplicateChecker -> duplicates.putAll(duplicateChecker.findDuplicates(classAllDependenciesMap)));

    duplicates.keySet().forEach(
        key -> duplicates.get(key).stream().filter(x -> x.isAnnotationPresent(Default.class))
            .findFirst().orElseThrow(
                () -> new DuplicateBeansFound(
                    "No qualifying bean of type" + key.getName() + " exists!")
            ));
    // TODO
  }

  private void helper(Class<?> aClass, Map<Class<?>, Boolean> visited,
      Map<Class<?>, List<Class<?>>> classListMap) {
    var list = classListMap.getOrDefault(aClass, new ArrayList<>());

    if (visited.get(aClass) == null || !visited.get(aClass)) {
      visited.put(aClass, true);
      list.forEach(x -> helper(x, visited, classListMap));
      visited.put(aClass, false);
    } else {
      throw new CilcularDependencyFoundExcpetion("Cycle!");
    }

  }
}

