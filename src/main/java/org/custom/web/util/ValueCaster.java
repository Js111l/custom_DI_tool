package org.custom.web.util;

public final class ValueCaster {
  public static Object cast(String value, Class<?> type) {

    switch (type.getSimpleName()) {
      case "Integer", "int" -> {
        return Integer.valueOf(value);
      }
      case "Double", "double" -> {
        return Double.valueOf(value);
      }
      case "Boolean", "boolean" -> {
        return Boolean.valueOf(value);
      }
      case "Float", "float" -> {
        return Float.valueOf(value);
      }
      case "Long", "long" -> {
        return Long.valueOf(value);
      }
      case "String" -> {
        return value;
      }
      default -> {
        return null;
      }
    }
  }
}
