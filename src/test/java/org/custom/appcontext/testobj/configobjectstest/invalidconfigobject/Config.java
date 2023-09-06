package org.custom.appcontext.testobj.configobjectstest.invalidconfigobject;

import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;
import org.custom.appcontext.testobj.configobjectstest.propeconfigobject.ExampleObj;

@ConfigBeanDefinitions
public class Config {

  @BeanDef
  public ExampleObj someBean() {
    return new ExampleObj();
  }

  @BeanDef
  public ExampleObj someBean2() {
    return new ExampleObj();
  }
}
