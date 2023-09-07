package org.custom.core.context.factory.contextcomponents;

import java.util.List;
import org.custom.core.checker.context.BeanDefChecker;
import org.custom.core.checker.context.ContextChecker;
import org.custom.core.checker.context.ItemChecker;
import org.custom.core.checker.duplicate.DuplicateChecker;
import org.custom.core.checker.duplicate.ItemConfigDuplicateChecker;
import org.custom.core.dependency.ConfigDependencyGetter;
import org.custom.core.dependency.ConstructorDependencyGetter;
import org.custom.core.dependency.DependencyGetter;
import org.custom.core.dependency.FieldDependencyGetter;
import org.custom.core.injector.BeanDefinitonInjector;
import org.custom.core.injector.FieldInjector;
import org.custom.core.injector.Injector;
import org.custom.core.scanner.BeanDefinitionClassScanner;
import org.custom.core.scanner.ControllerScanner;
import org.custom.core.scanner.ItemScanner;
import org.custom.core.scanner.Scanner;

public final class DefaultContextComponentsFactory implements ContextComponentsFactory {

  @Override
  public List<Injector> createInjectors() {
    return List.of(new FieldInjector(), new BeanDefinitonInjector());
  }

  @Override
  public List<Scanner<Class<?>>> createScanners() {
    return List.of(new BeanDefinitionClassScanner(), new ItemScanner(), new ControllerScanner());
  }

  @Override
  public List<DependencyGetter<Class<?>, ?>> createDependencyGetters() {
    return List.of(new ConfigDependencyGetter(),
        new ConstructorDependencyGetter(),
        new FieldDependencyGetter());
  }

  @Override
  public List<ContextChecker> createContextChecker() {
    return List.of(new BeanDefChecker(), new ItemChecker());
  }

  @Override
  public List<DuplicateChecker> createDuplicateCheckers() {
    return List.of(new ItemConfigDuplicateChecker());
  }
}
