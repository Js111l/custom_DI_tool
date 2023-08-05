package org.custom.appcontext;


import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.custom.Utils.ONE;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.custom.annotations.Default;
import org.custom.dependency.ConfigDependencyGetter;
import org.custom.dependency.ConstructorDependencyGetter;
import org.custom.dependency.FieldDependencyGetter;
import org.custom.exceptions.DuplicateBeansFound;
import org.custom.exceptions.NoSuchBeanFoundException;
import org.custom.injector.BeanDefinitonInjector;
import org.custom.injector.FieldInjector;
import org.custom.scanner.BeanDefinitionClassScanner;
import org.custom.scanner.ItemScanner;

public class AppContext implements CustomApplicationContext {

  private final Map<Class<?>, Object> beans;
  private String[] packagesToScan;

  public AppContext() {
    this.beans = initializeContainer();
  }

  public AppContext(String... packagesToScan) {
    this.packagesToScan = packagesToScan;
    this.beans = initializeContainer();
  }

  public Map<Class<?>, Object> getBeans() {
    return beans;
  }

  private Map<Class<?>, Object> initializeContainer() {
    AppContextInitializer contextInitializer;
    if (isNotEmpty(packagesToScan)) {
      contextInitializer = new AppContextInitializer(this.packagesToScan);
    } else {
      contextInitializer = new AppContextInitializer();
    }
    contextInitializer.setInjectors(List.of(new FieldInjector(), new BeanDefinitonInjector()));
    contextInitializer.setScanner(List.of(new ItemScanner(), new BeanDefinitionClassScanner()));
    contextInitializer.setDependencyGetters(
        List.of(new ConstructorDependencyGetter(), new FieldDependencyGetter(),
            new ConfigDependencyGetter()));
    return contextInitializer.initializeContext();
  }

  @Override
  public Object getItem(String name) {
    var key = beans.keySet().stream().filter(clazz -> clazz.getSimpleName().equals(name))
        .findFirst()
        .orElseThrow(
            () -> new NoSuchBeanFoundException("No such bean with name" + name + "found!")
        );
    return beans.get(key);
  }

  public Map<Class<?>, Object> getContext() {
    return beans;
  }

  @Override
  public Object getItem(Class<?> requiredClass) {
    Class<?> classToSearch = requiredClass;
    if (classToSearch.isInterface()) {
      var aInterface = classToSearch;
      var list = this.beans.keySet().stream().filter(aInterface::isAssignableFrom)
          .toList();
      System.out.println(aInterface);
      System.out.println(beans.keySet());
      if (list.size() > ONE) {
        var defaults = list.stream().filter(x -> x.isAnnotationPresent(Default.class)).toList();
        if (defaults.size() > ONE) {
          throw new DuplicateBeansFound("more than one default annotation!");
        }
        var clazz = defaults.stream().findFirst()
            .orElseThrow(
                () -> new DuplicateBeansFound(
                    "No qualifying bean of type " + requiredClass.getName() + " exists!")
            );
        {
          classToSearch = clazz;
        }
      }
    }
    return Optional.ofNullable(beans.get(classToSearch)).orElseThrow(() ->
        new NoSuchBeanFoundException(
            "No qualifying bean of type " + requiredClass.getName() + " exists!"));
  }
}

