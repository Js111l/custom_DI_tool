package org.custom.injector;

import java.util.List;
import java.util.Map;

public abstract class Injector {

  protected List<Class<?>> classSet;
  protected Map<Class<?>, Object> containerBeans;

  public abstract void setContainerBeans(Map<Class<?>, Object> containerBeans);

  public abstract void setClassSet(List<Class<?>> classSet);

  public abstract void inject();
}
