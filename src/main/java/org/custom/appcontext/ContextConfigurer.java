package org.custom.appcontext;

import java.util.Map;
import org.custom.context.factory.contextinitializer.ContextInitializerFactory;

public class ContextConfigurer {

  private final ContextInitializerFactory contextInitializerFactory;

  public ContextConfigurer(ContextInitializerFactory contextInitializerFactory) {
    this.contextInitializerFactory = contextInitializerFactory;
  }

  private void initializeContext() {
    var contextInitializer = this.contextInitializerFactory.createContextInitializer();
    contextInitializer.initializeContext();
  }

  public Map<Class<?>, Object> getInitializedContext() {
    initializeContext();
    return this.contextInitializerFactory.getContext();
  }
}
