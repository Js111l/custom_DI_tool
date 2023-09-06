package org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.nodefaultoutsideconfig;

import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;

@ConfigBeanDefinitions
public class Config {

  @BeanDef
  public DefaultTest2 someBean() {
    return new DefaultTest2();
  }

}
