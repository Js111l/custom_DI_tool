package org.custom.core.appcontext;

import static org.custom.core.utils.ClassUtil.ONE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.custom.core.annotations.Default;
import org.custom.core.context.factory.contextcomponents.DefaultContextComponentsFactory;
import org.custom.core.context.factory.contextinitializer.DefaultContextInitializerFactory;
import org.custom.core.exceptions.DuplicateBeansFound;
import org.custom.core.exceptions.NoSuchBeanFoundException;
import org.custom.web.WebContext;

public class AppContext implements CustomApplicationContext {

  private final Map<Class<?>, Object> beans;
  private int port = 8080;

  public AppContext() {
    final var configurer =
        new ContextConfigurer(
            new DefaultContextInitializerFactory(new DefaultContextComponentsFactory()));
    this.beans = configurer.getInitializedContext();

    var webContext = new WebContext();
    System.out.println(beans.get("ControllerWithWired"));
    webContext.configure(this.beans, port);
    webContext.initialize();
  }

  public AppContext(String... packagesToScan) {
    final var configurer =
        new ContextConfigurer(
            new DefaultContextInitializerFactory(
                new DefaultContextComponentsFactory(), packagesToScan));
    this.beans = configurer.getInitializedContext();

    var webContext = new WebContext();
    webContext.configure(this.beans, port);
    webContext.initialize();
  }
  @Override
  public Object getItem(String name) {
    var key =
        beans.keySet().stream()
            .filter(clazz -> clazz.getSimpleName().equals(name))
            .findFirst()
            .orElseThrow(
                () -> new NoSuchBeanFoundException("No such bean with name" + name + "found!"));
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
      classList =
          new ArrayList<>(
              this.beans.keySet().stream().filter(classToSearch::isAssignableFrom).toList());
    }
    var defaults = classList.stream().filter(x -> x.isAnnotationPresent(Default.class)).toList();

    if (defaults.size() > ONE) {
      throw new DuplicateBeansFound("more than one default annotation!");
    }
    if (classList.size() > ONE) {

      classToSearch =
          defaults.stream()
              .findFirst()
              .orElseThrow(
                  () ->
                      new DuplicateBeansFound(
                          "No qualifying bean of type " + requiredClass.getName() + " exists!"));
    }
    return Optional.ofNullable(beans.get(classToSearch))
        .orElseThrow(
            () ->
                new NoSuchBeanFoundException(
                    "No qualifying bean of type " + requiredClass.getName() + " exists!"));
  }
}
