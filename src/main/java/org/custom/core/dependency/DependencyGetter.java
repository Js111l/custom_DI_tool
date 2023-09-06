package org.custom.core.dependency;

import java.util.List;
import java.util.Map;

public interface DependencyGetter<T, L> {

  Map<T, List<L>> getDependencies(List<Class<?>> classes);
}
