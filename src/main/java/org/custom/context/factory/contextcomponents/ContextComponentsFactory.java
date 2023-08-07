package org.custom.context.factory.contextcomponents;

import java.util.List;
import org.custom.checker.context.ContextChecker;
import org.custom.checker.duplicate.DuplicateChecker;
import org.custom.dependency.DependencyGetter;
import org.custom.injector.Injector;
import org.custom.scanner.Scanner;

public interface ContextComponentsFactory {

  List<Injector> createInjectors();

  List<Scanner<Class<?>>> createScanners();

  List<DependencyGetter<Class<?>, ?>> createDependencyGetters();

  List<ContextChecker> createContextChecker();

  List<DuplicateChecker> createDuplicateCheckers();
}
