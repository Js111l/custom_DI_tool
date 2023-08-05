package org.custom.appcontext;

import java.util.List;
import org.custom.dependency.DependencyGetter;
import org.custom.injector.Injector;
import org.custom.scanner.Scanner;

public abstract class ContextInitializer {

  protected List<Scanner<Class<?>>> scanners;
  protected List<Injector> injectors;

  protected List<DependencyGetter> dependencyGetters;

  abstract void scanItems();

  abstract void scanDependencies();

  abstract void initializeItems();

}
