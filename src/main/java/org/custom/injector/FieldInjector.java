package org.custom.injector;


import java.util.Map;
import java.util.Set;
import org.custom.dependency.FieldDependencyGetter;
import org.custom.initializer.ClassInitializer;

public class FieldInjector extends Injector {

  @Override
  public void setContainerBeans(Map<Class<?>, Object> containerBeans) {
    this.containerBeans = containerBeans;
  }

  @Override
  public void setClassSet(Set<Class<?>> classSet) {
    this.classSet = classSet;
  }


  @Override
  public void inject() {
    //TODO
    var classFieldsMap = new FieldDependencyGetter().getDependencies(
        this.classSet.stream().toList());

    classFieldsMap.keySet().forEach(aClass -> {
      if (containerBeans.get(aClass) != null) {
        var obj = containerBeans.get(aClass);
        var unInitializedFields = classFieldsMap.get(aClass);

        unInitializedFields.forEach(field -> {
          var initializer = new ClassInitializer(this.classSet.stream().toList());

          var fieldType = field.getType();
          var initializedObject = initializer.initialize(fieldType);
          field.setAccessible(true);
          try {
            field.set(obj, initializedObject);
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
          containerBeans.put(obj.getClass(), obj);
        });
      } else {

      }
    });
  }

}
