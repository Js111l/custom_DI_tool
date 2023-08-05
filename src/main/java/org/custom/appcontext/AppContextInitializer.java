package org.custom.appcontext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.custom.dependency.DependencyGetter;
import org.custom.inspector.DependencyInspector;
import org.custom.initializer.ClassInitializer;
import org.custom.injector.Injector;
import org.custom.inspector.ClassInspector;
import org.custom.scanner.Scanner;

public class AppContextInitializer extends ContextInitializer {

  private final Map<Class<?>, Object> context = new HashMap<>();
  private List<Class<?>> scannedClasses;
  private final List<Class<?>> items = new ArrayList<>();

  public AppContextInitializer(String[] packagesToScan) {
    this.scannedClasses = new ClassInspector(packagesToScan).getAllClasses();
  }

  public AppContextInitializer() {
    this.scannedClasses = new ClassInspector().getAllClasses();
  }

  public void setInjectors(List<Injector> injectors) {
    this.injectors = new ArrayList<>(injectors);
  }

  public void setScanner(List<Scanner<Class<?>>> scanners) {
    this.scanners = new ArrayList<>(scanners);
  }

  public void setDependencyGetters(List<DependencyGetter<?, ?>> dependencyGetters) {
    this.dependencyGetters = new ArrayList<>(dependencyGetters);
  }

  public Map<Class<?>, Object> initializeContext() {
    scanItems();
    initializeItems();
    scanDependencies();
    injectDependencies();
    return this.context;
  }

  @Override
  void scanItems() {
    this.scanners.forEach(
        scanner ->
            this.items.addAll(scanner.getScannedComponentsFrom(this.scannedClasses)));
  }

  @Override
  void scanDependencies() {

    var classAllDependenciesMap = new HashMap<Class<?>, List<Class<?>>>();
    this.dependencyGetters.forEach(dependencyGetter -> classAllDependenciesMap.putAll(
        dependencyGetter.getDependencies(this.items)));

//    var classAllDependenciesMap = mergeMaps(constructorDependencies, fieldDependencies); //TODO

    var dependencyInspector = new DependencyInspector();
    dependencyInspector.checkForCycles(classAllDependenciesMap);
    dependencyInspector.checkForDuplicates(classAllDependenciesMap);
  }


  @Override
  void initializeItems() {
    var classInitializer = new ClassInitializer(this.items);

    this.items.forEach(aClass -> {
      var initializedObject = classInitializer.initialize(aClass);
      this.context.put(aClass, initializedObject);
    });

  }

  public void injectDependencies() {
    this.injectors.forEach(injector -> {
      injector.setContainerBeans(this.context);
      injector.setClassSet(this.context.keySet());
      injector.inject();// TODO
    });
  }

}
