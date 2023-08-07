package org.custom.checker.context;

import java.util.List;
import org.custom.annotations.Item;

public class ItemChecker implements ContextChecker {

  @Override
  public boolean isClassInContext(Class<?> classToTest, List<Class<?>> context) {
    return context.stream().filter(x -> x.isAnnotationPresent(Item.class))
        .anyMatch(x -> x.equals(classToTest));
  }
}
