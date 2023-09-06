package org.custom.core.checker.context;

import java.util.List;

public interface ContextChecker {

  boolean isClassInContext(Class<?> classToTest, List<Class<?>> context);
}
