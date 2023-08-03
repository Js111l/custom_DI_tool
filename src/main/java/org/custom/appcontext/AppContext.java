package org.custom.appcontext;


import java.util.List;
import java.util.Map;
import org.custom.injector.BeanDefinitonInjector;
import org.custom.injector.FieldInjector;
import org.custom.scanner.ConfigScanner;
import org.custom.scanner.ItemScanner;

public class AppContext implements CustomApplicationContext {

  private final Map<Class<?>, Object> beans;

  public AppContext() {
    this.beans = initializeContainer();
  }

  public Map<Class<?>, Object> getBeans() {
    return beans;
  }

  private Map<Class<?>, Object> initializeContainer() {
    AppContextInitializer contextInitializer = new AppContextInitializer();
    contextInitializer.setInjectors(List.of(new FieldInjector(), new BeanDefinitonInjector()));
    contextInitializer.setScanner(List.of(new ItemScanner(), new ConfigScanner()));
    return contextInitializer.initializeContext();
  }

  @Override
  public Object getItem(String name) {
    var key = beans.keySet().stream().filter(x -> x.getSimpleName().equals(name)).findFirst()
        .orElseThrow();//TODO
    return beans.get(key);
  }

  @Override
  public Object getItem(Class<?> requiredClass) {
    return beans.get(requiredClass);
  }
}

