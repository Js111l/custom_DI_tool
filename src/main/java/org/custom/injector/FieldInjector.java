package org.custom.injector;

import static org.custom.Utils.getFieldAnnotations;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.custom.dependency.DependencyGetter;
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
    var initializer = new ClassInitializer();
    var classFieldsMap = new DependencyGetter().getFieldDependencies(
        this.classSet.stream().toList(), getFieldAnnotations(this.classSet.stream().toList()));

    classFieldsMap.keySet().forEach(aClass -> {
      if (containerBeans.get(aClass) != null) {
        var obj = containerBeans.get(aClass);
        var unInitializedFields = classFieldsMap.get(aClass);

        unInitializedFields.forEach(field -> {
          var fieldType = field.getType();
          var cons = Arrays.stream(fieldType.getConstructors()).findFirst().orElseThrow();//TODO
          var initializedObject = initializer.initialize(cons);
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
