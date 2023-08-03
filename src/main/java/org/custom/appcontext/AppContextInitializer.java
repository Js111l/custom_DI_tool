package org.custom.appcontext;

import static org.custom.Utils.getFieldAnnotations;
import static org.custom.Utils.mergeMaps;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.custom.annotations.Wired;
import org.custom.dependency.DependencyGetter;
import org.custom.inspector.DependencyInspector;
import org.custom.initializer.ClassInitializer;
import org.custom.injector.Injector;
import org.custom.inspector.ClassInspector;
import org.custom.scanner.Scanner;

import static org.custom.Utils.getConstructor;

public class AppContextInitializer extends ContextInitializer {

  private final Map<Class<?>, Object> context = new HashMap<>();
  private final List<Class<?>> scannedClasses = new ClassInspector().getAllClasses();
  private final List<Class<?>> items = new ArrayList<>();

  public void setInjectors(List<Injector> injectors) {
    this.injectors = new ArrayList<>(injectors);
  }

  public void setScanner(List<Scanner<Class<?>>> scanners) {
    this.scanners = new ArrayList<>(scanners);
  }

  public Map<Class<?>, Object> initializeContext() {
    scanItems();
    initializeItems();
    scanDependencies();
    injectFieldDependencies();
    return this.context;
  }

  @Override
  void scanItems() {
    this.scanners.forEach(
        scanner -> this.items.addAll(scanner.getScannedComponentsFrom(this.scannedClasses)));
  }

  @Override
  void scanDependencies() {
    var getter = new DependencyGetter();
    var constructorDependencies = getter.getConstructorDependencies(this.items);

    var fieldDependencies = getter.getFieldDependencies(this.items,
        getFieldAnnotations(scannedClasses));
    var classAllDependenciesMap = mergeMaps(constructorDependencies, fieldDependencies);

    var dependencyInspector = new DependencyInspector();
    dependencyInspector.checkForCycles(classAllDependenciesMap);
  }


  @Override
  void initializeItems() {
    var classInitializer = new ClassInitializer();

    this.items.forEach(aClass -> {
      var constructor = getConstructor(aClass);
      var initializedObject = classInitializer.initialize(constructor);
      this.context.put(aClass, initializedObject);
    });
  }

  public void injectFieldDependencies() {
    this.injectors.forEach(injector -> {
      injector.setContainerBeans(this.context);
      injector.setClassSet(this.context.keySet());
      injector.inject();// TODO
    });
  }

}
