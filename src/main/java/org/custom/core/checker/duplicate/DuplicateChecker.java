package org.custom.core.checker.duplicate;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;

public interface DuplicateChecker {
Multimap<Class<?>, Class<?>> findDuplicates(Map<Class<?>, List<Class<?>>> classAllDependenciesMap);
}
