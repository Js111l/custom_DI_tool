package org.custom.core.context.scanner;

import java.util.List;
import org.custom.core.scanner.Scanner;

public class DefaultContextItemScanner implements ContextItemScanner {

  private final List<Scanner<Class<?>>> scanners;
  private final List<Class<?>> items;
  private final List<Class<?>> scannedClasses;

  public DefaultContextItemScanner(List<Scanner<Class<?>>> scanners, List<Class<?>> items,
      List<Class<?>> scannedClasses) {
    this.scanners = scanners;
    this.items = items;
    this.scannedClasses = scannedClasses;
  }

  @Override
  public void scanItems() {
    this.scanners.forEach(
        scanner ->
            this.items.addAll(scanner.getScannedComponentsFrom(this.scannedClasses)));
  }
}
