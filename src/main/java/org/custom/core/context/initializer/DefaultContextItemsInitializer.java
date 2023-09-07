package org.custom.core.context.initializer;

import java.util.List;
import java.util.Map;
import org.custom.core.checker.context.ContextChecker;
import org.custom.core.initializer.ClassInitializer;

public class DefaultContextItemsInitializer implements ContextItemInitializer {

  private final List<ContextChecker> contextCheckers;
  private final List<Class<?>> items;
  private final Map<Class<?>, Object> context;

  public DefaultContextItemsInitializer(List<ContextChecker> contextCheckers,
      List<Class<?>> items, Map<Class<?>, Object> context) {
    this.contextCheckers = contextCheckers;
    this.items = items;
    this.context = context;
  }

  @Override
  public void initializeItems() {
    final var classInitializer = new ClassInitializer(this.items, this.contextCheckers);

    this.items.forEach(aClass -> {
      final var initializedObject = classInitializer.initialize(aClass);
      this.context.put(aClass, initializedObject);
    });
  }
}
