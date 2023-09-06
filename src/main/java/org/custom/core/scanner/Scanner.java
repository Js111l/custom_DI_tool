package org.custom.core.scanner;

import java.util.List;

public interface Scanner<T> {
   List<T> getScannedComponentsFrom(List<T> scannedClasses);

}

