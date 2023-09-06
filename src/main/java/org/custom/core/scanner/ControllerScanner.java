package org.custom.core.scanner;

import java.util.List;
import java.util.stream.Collectors;
import org.custom.web.annotations.RestController;

public class ControllerScanner implements Scanner<Class<?>>{

  @Override
  public List<Class<?>> getScannedComponentsFrom(List<Class<?>> scannedClasses) {
    return scannedClasses.stream().filter(cls->cls.isAnnotationPresent(RestController.class))
        .collect(Collectors.toList());
  }
}
