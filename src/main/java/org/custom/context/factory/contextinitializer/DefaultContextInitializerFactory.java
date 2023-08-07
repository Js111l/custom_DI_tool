package org.custom.context.factory.contextinitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.custom.appcontext.initializer.AppContextInitializer;
import org.custom.appcontext.initializer.ContextInitializer;
import org.custom.context.dependency.DefaultContextDependencyScanner;
import org.custom.context.factory.contextcomponents.ContextComponentsFactory;
import org.custom.context.initializer.DefaultContextItemsInitializer;
import org.custom.context.injector.DefaultAppContextDependencyInjector;
import org.custom.context.scanner.DefaultContextItemScanner;
import org.custom.inspector.ClassInspector;

public class DefaultContextInitializerFactory implements ContextInitializerFactory {

  private final ContextComponentsFactory contextComponentFactory;
  private List<Class<?>> classList = new ArrayList<>();
  private final List<Class<?>> scannedClasses;

  private Map<Class<?>, Object> context = new HashMap<>();

  public DefaultContextInitializerFactory(ContextComponentsFactory contextComponentFactory) {
    this.contextComponentFactory = contextComponentFactory;
    this.scannedClasses = new ClassInspector().getAllClasses();
  }

  public DefaultContextInitializerFactory(ContextComponentsFactory contextComponentFactory,
      String... packagesToScan) {
    this.contextComponentFactory = contextComponentFactory;
    this.scannedClasses = new ClassInspector(packagesToScan).getAllClasses();
  }

  @Override
  public ContextInitializer createContextInitializer() {
    return new AppContextInitializer(
        new DefaultContextDependencyScanner(this.contextComponentFactory.createDependencyGetters(),
            this.contextComponentFactory.createDuplicateCheckers(), this.classList),
        new DefaultContextItemsInitializer(this.contextComponentFactory.createContextChecker(),
            this.classList, this.context),
        new DefaultAppContextDependencyInjector(this.contextComponentFactory.createInjectors(),
            this.context, this.classList),
        new DefaultContextItemScanner(this.contextComponentFactory.createScanners(), this.classList,
            this.scannedClasses)
    );
  }

  public Map<Class<?>, Object> getContext() {
    return context;
  }
}
