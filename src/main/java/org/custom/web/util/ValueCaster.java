package org.custom.web.util;

public final class ValueCaster {

  public static Object cast(String value, Class<?> type) {
    Object castedObject;
    switch (type.getSimpleName()) {
      case "Integer", "int" -> castedObject = Integer.valueOf(value);

      case "Double", "double" -> castedObject = Double.valueOf(value);

      case "Boolean", "boolean" -> castedObject = Boolean.valueOf(value);

      case "Float", "float" -> castedObject = Float.valueOf(value);

      case "Long", "long" -> castedObject = Long.valueOf(value);

      case "String" -> castedObject = value;

      default -> throw new IllegalStateException("Unexpected value: " + type.getSimpleName());
    }
    return castedObject;
  }
}
