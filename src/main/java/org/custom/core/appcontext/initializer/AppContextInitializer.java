package org.custom.core.appcontext.initializer;

import org.custom.core.context.dependency.ContextDependencyScanner;
import org.custom.core.context.initializer.ContextItemInitializer;
import org.custom.core.context.injector.ContextDependencyInjector;
import org.custom.core.context.scanner.ContextItemScanner;

public class AppContextInitializer implements ContextInitializer {

  private final ContextDependencyScanner contextDependencyScanner;
  private final ContextItemInitializer contextItemInitializer;

  private final ContextDependencyInjector contextDependencyInjector;

  private final ContextItemScanner contextItemScanner;

  public AppContextInitializer(ContextDependencyScanner contextDependencyScanner,
      ContextItemInitializer contextItemInitializer,
      ContextDependencyInjector contextDependencyInjector, ContextItemScanner contextItemScanner) {
    this.contextDependencyScanner = contextDependencyScanner;
    this.contextItemInitializer = contextItemInitializer;
    this.contextDependencyInjector = contextDependencyInjector;
    this.contextItemScanner = contextItemScanner;
  }

  @Override
  public void initializeContext() {
    this.contextItemScanner.scanItems();
    this.contextDependencyScanner.scanDependencies();
    this.contextItemInitializer.initializeItems();
    this.contextDependencyInjector.injectDependencies();
  }

}
