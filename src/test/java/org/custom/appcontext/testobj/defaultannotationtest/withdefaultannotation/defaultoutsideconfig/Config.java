package org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.defaultoutsideconfig;

import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;

@ConfigBeanDefinitions
public class Config {

  @BeanDef
  public DefaultTest someBean() {
    return new DefaultTest();
  }
}
