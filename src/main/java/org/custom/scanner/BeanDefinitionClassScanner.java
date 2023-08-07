package org.custom.scanner;

import java.util.List;
import org.custom.annotations.ConfigBeanDefinitions;

public class BeanDefinitionClassScanner implements Scanner<Class<?>> {

  @Override
  public List<Class<?>> getScannedComponentsFrom(List<Class<?>> scannedClasses) {
    return scannedClasses.stream()
        .filter(x -> x.isAnnotationPresent(ConfigBeanDefinitions.class))
        .toList();
  }
}
