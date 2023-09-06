package org.custom.appcontext.testobj.configobjectstest.propeconfigobject;

import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;
import org.custom.core.annotations.Default;

@ConfigBeanDefinitions
public class Config {

  @BeanDef
  public ExampleObj someBean() {
    return new ExampleObj();
  }

  @BeanDef
  public ObjectC cBean() {
    return new ObjectC();
  }

  @BeanDef
  @Default
  public ExampleObj someBean2() {
    return new ExampleObj();
  }
}
