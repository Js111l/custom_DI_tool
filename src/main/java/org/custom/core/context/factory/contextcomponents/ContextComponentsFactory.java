package org.custom.core.context.factory.contextcomponents;

import java.util.List;
import org.custom.core.checker.context.ContextChecker;
import org.custom.core.checker.duplicate.DuplicateChecker;
import org.custom.core.dependency.DependencyGetter;
import org.custom.core.injector.Injector;
import org.custom.core.scanner.Scanner;

public interface ContextComponentsFactory {

  List<Injector> createInjectors();

  List<Scanner<Class<?>>> createScanners();

  List<DependencyGetter<Class<?>, ?>> createDependencyGetters();

  List<ContextChecker> createContextChecker();

  List<DuplicateChecker> createDuplicateCheckers();
}
