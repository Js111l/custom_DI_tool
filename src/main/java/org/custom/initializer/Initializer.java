package org.custom.initializer;

import java.lang.reflect.Constructor;

public interface Initializer {

  Object initialize(Constructor<?> constructor);
}
