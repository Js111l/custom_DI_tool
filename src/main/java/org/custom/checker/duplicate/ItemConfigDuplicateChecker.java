package org.custom.checker.duplicate;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.custom.annotations.Item;

public class ItemConfigDuplicateChecker implements DuplicateChecker {

  @Override
  public Multimap<Class<?>, Class<?>> findDuplicates(
      Map<Class<?>, List<Class<?>>> classAllDependenciesMap) {
    var duplicates = ArrayListMultimap.<Class<?>, Class<?>>create();

    classAllDependenciesMap.values().stream().flatMap(Collection::stream).forEach(value -> {
      if (classAllDependenciesMap.containsKey(value)) {
        var key = getKeyFromValue(value, classAllDependenciesMap);
        if (!key.isAnnotationPresent(Item.class)) {
          duplicates.put(value, value);
        }
      }
    });
    return duplicates;
  }

  private Class<?> getKeyFromValue(Class<?> value, Map<Class<?>, List<Class<?>>> map) {
    return map.entrySet().stream()
        .filter(entry -> entry.getValue().contains(value))
        .map(Entry::getKey).findFirst()
        .orElseThrow();
  }

}
