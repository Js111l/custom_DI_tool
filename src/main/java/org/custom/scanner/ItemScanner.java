package org.custom.scanner;

import org.custom.annotations.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemScanner implements Scanner<Class<?>> {
  @Override
  public List<Class<?>> getScannedComponentsFrom(List<Class<?>> scannedClasses) {
    return scannedClasses.stream().filter(x -> x.isAnnotationPresent(Item.class))
        .collect(Collectors.toList());
  }
}
