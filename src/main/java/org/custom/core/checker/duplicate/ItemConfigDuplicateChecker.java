package org.custom.core.checker.duplicate;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.custom.core.annotations.ConfigBeanDefinitions;
import org.custom.core.annotations.Item;

public final class ItemConfigDuplicateChecker implements DuplicateChecker {

  @Override
  public Multimap<Class<?>, Class<?>> findDuplicates(
      Map<Class<?>, List<Class<?>>> classAllDependenciesMap) {
    final var duplicates = ArrayListMultimap.<Class<?>, Class<?>>create();

    classAllDependenciesMap.entrySet().stream().filter(entry -> entry.getKey().isAnnotationPresent(
            ConfigBeanDefinitions.class)).map(entry -> entry.getValue()).flatMap(Collection::stream)
        .forEach(value -> {
          if (classAllDependenciesMap.containsKey(value)) {
            final var key = getKeyFromValue(value, classAllDependenciesMap);
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
