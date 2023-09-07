package org.custom.core.utils;


import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ClassUtil {

  public static final int FIRST_ELEMENT = 0;
  public static final int ONE = 1;
  public static final int ZERO = 0;


  public static Constructor<?> getConstructor(Class<?> cls) {
    return Arrays.stream(cls.getConstructors()).findFirst().orElseThrow();
  }


}
