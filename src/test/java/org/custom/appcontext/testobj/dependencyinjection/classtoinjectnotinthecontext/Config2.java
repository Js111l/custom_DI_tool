package org.custom.appcontext.testobj.dependencyinjection.classtoinjectnotinthecontext;

import org.custom.annotations.ConfigBeanDefinitions;

@ConfigBeanDefinitions
public class Config2 {
  public B2 get() {
    return new B2();
  }
}
