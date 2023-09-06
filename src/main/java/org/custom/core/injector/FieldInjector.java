package org.custom.core.injector;


import java.util.List;
import java.util.Map;
import org.custom.core.dependency.FieldDependencyGetter;
import org.custom.core.initializer.ClassInitializer;

public class FieldInjector extends Injector {

  @Override
  public void setContainerBeans(Map<Class<?>, Object> containerBeans) {
    this.containerBeans = containerBeans;
  }

  @Override
  public void setClassSet(List<Class<?>> classSet) {
    this.classSet = classSet;
  }


  @Override
  public void inject() {
    var classFieldsMap = new FieldDependencyGetter().getDependencies(
        this.classSet.stream().toList());

    classFieldsMap.keySet().forEach(aClass -> {
      if (containerBeans.get(aClass) != null) {
        var obj = containerBeans.get(aClass);
        var unInitializedFields = classFieldsMap.get(aClass);

        unInitializedFields.forEach(field -> {
          var initializer = new ClassInitializer(this.classSet,
              List.of());

          var fieldType = field.getType();
          var initializedObject = initializer.initialize(fieldType);
          field.setAccessible(true);
          try {
            field.set(obj, initializedObject);
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);  //TODO
          }
          containerBeans.put(obj.getClass(), obj);
        });
      } else {
// Todo
      }
    });
  }

}
