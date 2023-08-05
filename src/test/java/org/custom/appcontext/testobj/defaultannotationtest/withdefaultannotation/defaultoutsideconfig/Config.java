package org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.defaultoutsideconfig;

import org.custom.annotations.BeanDef;
import org.custom.annotations.ConfigBeanDefinitions;

@ConfigBeanDefinitions
public class Config {

  @BeanDef
  public DefaultTest someBean() {
    return new DefaultTest();
  }
}
