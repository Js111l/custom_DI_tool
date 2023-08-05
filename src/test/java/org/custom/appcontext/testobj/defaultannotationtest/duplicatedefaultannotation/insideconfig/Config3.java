package org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.insideconfig;

import org.custom.annotations.BeanDef;
import org.custom.annotations.ConfigBeanDefinitions;
import org.custom.annotations.Default;
import org.custom.appcontext.testobj.configobjectstest.propeconfigobject.ExampleObj;
import org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.nodefaultoutsideconfig.DefaultTest2;

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
