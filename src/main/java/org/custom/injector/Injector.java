package org.custom.injector;

import java.util.Map;
import java.util.Set;

public abstract class Injector {

  protected Set<Class<?>> classSet;
  protected Map<Class<?>, Object> containerBeans;

  public abstract void setContainerBeans(Map<Class<?>, Object> containerBeans);

  public abstract void setClassSet(Set<Class<?>> classSet);

  public abstract void inject();
}
