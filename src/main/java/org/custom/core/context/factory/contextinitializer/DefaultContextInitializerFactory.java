package org.custom.core.context.factory.contextinitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.custom.core.appcontext.initializer.AppContextInitializer;
import org.custom.core.appcontext.initializer.ContextInitializer;
import org.custom.core.context.dependency.DefaultContextDependencyScanner;
import org.custom.core.context.factory.contextcomponents.ContextComponentsFactory;
import org.custom.core.context.initializer.DefaultContextItemsInitializer;
import org.custom.core.context.injector.DefaultAppContextDependencyInjector;
import org.custom.core.context.scanner.DefaultContextItemScanner;
import org.custom.core.inspector.ClassInspector;

public class DefaultContextInitializerFactory implements ContextInitializerFactory {

  private final ContextComponentsFactory contextComponentFactory;
  private final List<Class<?>> classList = new ArrayList<>();
  private final List<Class<?>> scannedClasses;

  private final Map<Class<?>, Object> context = new HashMap<>();

  public DefaultContextInitializerFactory(ContextComponentsFactory contextComponentFactory) {
    this.contextComponentFactory = contextComponentFactory;
    this.scannedClasses = new ClassInspector().getAllClasses();
  }

  public DefaultContextInitializerFactory(
      ContextComponentsFactory contextComponentFactory, String... packagesToScan) {
    this.contextComponentFactory = contextComponentFactory;
    this.scannedClasses = new ClassInspector(packagesToScan).getAllClasses();
  }

  @Override
  public ContextInitializer createContextInitializer() {
    return new AppContextInitializer(
        new DefaultContextDependencyScanner(
            this.contextComponentFactory.createDependencyGetters(),
            this.contextComponentFactory.createDuplicateCheckers(),
            this.classList),
        new DefaultContextItemsInitializer(
            this.contextComponentFactory.createContextChecker(), this.classList, this.context),
        new DefaultAppContextDependencyInjector(
            this.contextComponentFactory.createInjectors(), this.context, this.classList),
        new DefaultContextItemScanner(
            this.contextComponentFactory.createScanners(), this.classList, this.scannedClasses));
  }

  @Override
  public Map<Class<?>, Object> getContext() {
    return context;
  }
}
