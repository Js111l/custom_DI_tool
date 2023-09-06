package org.custom.core.context.injector;

import java.util.List;
import java.util.Map;
import org.custom.core.injector.Injector;

public class DefaultAppContextDependencyInjector implements ContextDependencyInjector {

  private final List<Injector> injectors;
  private final Map<Class<?>, Object> context;
  private final List<Class<?>> items;


  public DefaultAppContextDependencyInjector(List<Injector> injectors,
      Map<Class<?>, Object> context, List<Class<?>> items) {
    this.injectors = injectors;
    this.context = context;
    this.items = items;
  }

  @Override
  public void injectDependencies() {
    this.injectors.forEach(injector -> {
      injector.setContainerBeans(this.context);
      injector.setClassSet(this.items);
      injector.inject();
    });
  }
}
