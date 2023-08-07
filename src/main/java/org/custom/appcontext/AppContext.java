package org.custom.appcontext;

import static org.custom.utils.ClassUtil.ONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.custom.annotations.Default;
import org.custom.context.factory.contextcomponents.DefaultContextComponentsFactory;
import org.custom.context.factory.contextinitializer.DefaultContextInitializerFactory;
import org.custom.exceptions.DuplicateBeansFound;
import org.custom.exceptions.NoSuchBeanFoundException;

public class AppContext implements CustomApplicationContext {

  private final Map<Class<?>, Object> beans;

  public AppContext() {
    var configurer = new ContextConfigurer(new DefaultContextInitializerFactory(
        new DefaultContextComponentsFactory()));
    this.beans = configurer.getInitializedContext();
  }

  public AppContext(String... packagesToScan) {
    var configurer = new ContextConfigurer(new DefaultContextInitializerFactory(
        new DefaultContextComponentsFactory(), packagesToScan));
    this.beans = configurer.getInitializedContext();
  }

  public Map<Class<?>, Object> getBeans() {
    return beans;
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
    // TODO looks ugly in current form

    Class<?> classToSearch = requiredClass;
    List<Class<?>> classList = new ArrayList<>();

    if (classToSearch.isInterface()) {
      var aInterface = classToSearch;
      classList = new ArrayList<>(this.beans.keySet().stream().filter(aInterface::isAssignableFrom)
          .toList());
    }
    var defaults = classList.stream().filter(x -> x.isAnnotationPresent(Default.class)).toList();

    if (defaults.size() > ONE) {
      throw new DuplicateBeansFound("more than one default annotation!");
    }
    if (classList.size() > ONE) {
      var clazz = defaults.stream().findFirst()
          .orElseThrow(
              () -> new DuplicateBeansFound(
                  "No qualifying bean of type " + requiredClass.getName() + " exists!")
          );

      classToSearch = clazz;
    }
    return Optional.ofNullable(beans.get(classToSearch)).orElseThrow(() ->
        new NoSuchBeanFoundException(
            "No qualifying bean of type " + requiredClass.getName() + " exists!"));
  }
}
