package org.custom.core.scanner;

import java.util.List;
import java.util.stream.Collectors;
import org.custom.core.annotations.Item;

public final class ItemScanner implements Scanner<Class<?>> {
  @Override
  public List<Class<?>> getScannedComponentsFrom(List<Class<?>> scannedClasses) {
    return scannedClasses.stream().filter(x -> x.isAnnotationPresent(Item.class))
        .collect(Collectors.toList());
  }
}
