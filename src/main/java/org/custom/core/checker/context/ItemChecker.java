package org.custom.core.checker.context;

import java.util.List;
import org.custom.core.annotations.Item;

public final class ItemChecker implements ContextChecker {

  @Override
  public boolean isClassInContext(Class<?> classToTest, List<Class<?>> context) {
    return context.stream().filter(x -> x.isAnnotationPresent(Item.class))
        .anyMatch(x -> x.equals(classToTest));
  }
}
