package org.custom.core.appcontext;


public interface CustomApplicationContext {

  Object getItem(String name);

  Object getItem(Class<?> requiredClass);

  // Map<String, Object> getItemsOfType(Class<?> requiredClass);
  // TODO
}
