package org.custom.checker.context;

import java.util.List;

public interface ContextChecker {

  boolean isClassInContext(Class<?> classToTest, List<Class<?>> context);
}
