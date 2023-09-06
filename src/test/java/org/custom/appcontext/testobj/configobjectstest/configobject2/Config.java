package org.custom.appcontext.testobj.configobjectstest.configobject2;

import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;
import org.custom.core.annotations.Default;
import org.custom.appcontext.testobj.configobjectstest.propeconfigobject.ExampleObj;
import org.custom.appcontext.testobj.configobjectstest.propeconfigobject.ObjectC;

@ConfigBeanDefinitions
public class Config {

  @BeanDef
  public ExampleObj someBean() {
    return new ExampleObj();
  }

  //@BeanDef  Object should not be registered in context
  public ObjectC cBean() {
    return new ObjectC();
  }

  @BeanDef
  @Default
  public ExampleObj someBean2() {
    return new ExampleObj();
  }
}
