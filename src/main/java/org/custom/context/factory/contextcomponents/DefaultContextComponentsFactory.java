package org.custom.context.factory.contextcomponents;

import java.util.List;
import org.custom.checker.context.BeanDefChecker;
import org.custom.checker.context.ContextChecker;
import org.custom.checker.context.ItemChecker;
import org.custom.checker.duplicate.DuplicateChecker;
import org.custom.checker.duplicate.ItemConfigDuplicateChecker;
import org.custom.dependency.ConfigDependencyGetter;
import org.custom.dependency.ConstructorDependencyGetter;
import org.custom.dependency.DependencyGetter;
import org.custom.dependency.FieldDependencyGetter;
import org.custom.injector.BeanDefinitonInjector;
import org.custom.injector.FieldInjector;
import org.custom.injector.Injector;
import org.custom.scanner.BeanDefinitionClassScanner;
import org.custom.scanner.ItemScanner;
import org.custom.scanner.Scanner;

public class DefaultContextComponentsFactory implements ContextComponentsFactory {

  @Override
  public List<Injector> createInjectors() {
    return List.of(new FieldInjector(), new BeanDefinitonInjector());
  }

  @Override
  public List<Scanner<Class<?>>> createScanners() {
    return List.of(new BeanDefinitionClassScanner(), new ItemScanner());
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
