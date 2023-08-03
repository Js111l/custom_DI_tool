package org.custom.appcontext.testobj;

import org.custom.annotations.BeanDef;
import org.custom.annotations.ConfigBeanDefinitions;
import org.custom.annotations.Default;

@ConfigBeanDefinitions
public class Config {

  @BeanDef
  public B someBean() {
    return new B();
  }

  @BeanDef
  @Default
  public B someBean2() {
    return new B();
  }
}
