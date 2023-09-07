package org.custom.core.appcontext;


public interface CustomApplicationContext {

  Object getItem(String name);

  Object getItem(Class<?> requiredClass);

}
