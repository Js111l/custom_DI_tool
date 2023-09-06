package org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.insideconfig;

import org.custom.core.annotations.BeanDef;
import org.custom.core.annotations.ConfigBeanDefinitions;
import org.custom.core.annotations.Default;
import org.custom.appcontext.testobj.configobjectstest.propeconfigobject.ExampleObj;

@ConfigBeanDefinitions
public class Config3 {

  @BeanDef
  @Default
  public ExampleObj returnObj() {
    return new ExampleObj();
  }

  @BeanDef
  @Default
  public ExampleObj returnObj2() {
    return new ExampleObj();
  }
}
