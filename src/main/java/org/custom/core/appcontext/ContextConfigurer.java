package org.custom.core.appcontext;

import java.util.Map;
import org.custom.core.appcontext.initializer.ContextInitializer;
import org.custom.core.context.factory.contextinitializer.ContextInitializerFactory;

public class ContextConfigurer {

  private final ContextInitializerFactory contextInitializerFactory;

  public ContextConfigurer(final ContextInitializerFactory contextInitializerFactory) {
    this.contextInitializerFactory = contextInitializerFactory;
  }

  private void initializeContext() {
    final var contextInitializer = getContextInitializer();
    contextInitializer.initializeContext();
  }

  private ContextInitializer getContextInitializer() {
    return this.contextInitializerFactory.createContextInitializer();
  }

  public Map<Class<?>, Object> getInitializedContext() {
    initializeContext();
    return this.contextInitializerFactory.getContext();
  }
}
