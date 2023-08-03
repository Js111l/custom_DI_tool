package org.custom.appcontext;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.custom.appcontext.testobj.A;
import org.custom.appcontext.testobj.B;

class AppContextTest {

  private final CustomApplicationContext context = new AppContext();

  @Test
  public void getItem_beanClass_properlyInjectedObject() {
    A obj = (A) this.context.getItem(A.class);
    assertThat(obj.getbObj()).isNotNull();
  }

  @Test
  public void getItem_beanClass_injectedObjectOfProperType() {
    A obj = (A) this.context.getItem(A.class);
    assertThat(obj.getbObj()).isInstanceOf(B.class);
  }
}