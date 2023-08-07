package org.custom.context.dependency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.custom.checker.duplicate.DuplicateChecker;
import org.custom.dependency.DependencyGetter;
import org.custom.inspector.DependencyInspector;

public class DefaultContextDependencyScanner implements ContextDependencyScanner {

  private final List<DependencyGetter<Class<?>, ?>> dependencyGetters;

  private final List<DuplicateChecker> duplicateCheckers;
  private final List<Class<?>> items;

  public DefaultContextDependencyScanner(
      List<DependencyGetter<Class<?>, ?>> dependencyGetters,
      List<DuplicateChecker> duplicateCheckers, List<Class<?>> items) {
    this.dependencyGetters = dependencyGetters;
    this.duplicateCheckers = duplicateCheckers;
    this.items = items;
  }

  @Override
  public void scanDependencies() {
    var classAllDependenciesMap = new HashMap<Class<?>, List<Class<?>>>();
    populateMap(classAllDependenciesMap);

    var dependencyInspector = new DependencyInspector();
    dependencyInspector.checkForCycles(classAllDependenciesMap);
    dependencyInspector.checkForDuplicates(classAllDependenciesMap, this.duplicateCheckers);
  }

  @SuppressWarnings("unchecked")
  private void populateMap(Map<Class<?>, List<Class<?>>> classAllDependenciesMap) {
    this.dependencyGetters.forEach(dependencyGetter -> {
      var dependencies = dependencyGetter.getDependencies(this.items);
      dependencies.forEach((key, value1) -> classAllDependenciesMap.computeIfAbsent(key,
          value -> (List<Class<?>>) value1));
    });
  }
}
