package org.custom.appcontext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.custom.appcontext.testobj.configobjectstest.configobject2.ObjectC2;
import org.custom.appcontext.testobj.configobjectstest.propeconfigobject.ExampleObj;
import org.custom.appcontext.testobj.configobjectstest.propeconfigobject.ObjectC;
import org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.outsideconfig.MessageService3;
import org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.sameinterfaceobjects.MessageService;
import org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.defaultoutsideconfig.DefaultTest;
import org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.sameinterfacetestobjects.EmailService2;
import org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.sameinterfacetestobjects.MessageService2;
import org.custom.appcontext.testobj.dependencyinjection.simpleobjects.A;
import org.custom.appcontext.testobj.dependencyinjection.simpleobjects.B;
import org.custom.core.appcontext.AppContext;
import org.custom.core.appcontext.CustomApplicationContext;
import org.custom.core.exceptions.DuplicateBeansFound;
import org.custom.core.exceptions.NoSuchBeanFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class AppContextTest {

  private static final String SIMPLE_DI_TESTOBJECTS_PACKAGE =
      "org.custom.appcontext.testobj.dependencyinjection.simpleobjects";
  private static final String SIMPLE_DI_TESTOBJECTS_CLASS_TO_INJECT_NOT_IN_CONTEXT_PACKAGE =
      "org.custom.appcontext.testobj.dependencyinjection.classtoinjectnotinthecontext";
  private static final String PROPER_CONFIG_TESTOBJECTS_PACKAGE =
      "org.custom.appcontext.testobj.configobjectstest.propeconfigobject";
  private static final String PROPER_CONFIG_TESTOBJECTS2_PACKAGE =
      "org.custom.appcontext.testobj.configobjectstest.propeconfigobject2";
  private static final String INVALID_CONFIG_TESTOBJECTS_PACKAGE =
      "org.custom.appcontext.testobj.configobjectstest.invalidconfigobject";

  private static final String DEFAULT_ANNOTATION_TESTOBJECT_PACKAGE =
      "org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.defaultoutsideconfig";
  private static final String NO_DEFAULT_ANNOTATION_TESTOBJECT_PACKAGE =
      "org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.nodefaultoutsideconfig";
  private static final String NO_DEFAULT_ANNOTATION_SAMEINTERFACE_TESTOBJECTS_PACKAGE =
      "org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.sameinterfaceobjects";
  private static final String DEFAULT_ANNOTATION_SAMEINTERFACE_TESTOBJECTS_PACKAGE =
      "org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.sameinterfacetestobjects";
  private static final String DUPLICATE_DEFAULT_OUTSIDECONFIG_ANNOTATION_TESTOBJECTS_PACKAGE =
      "org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.outsideconfig";
  private static final String DUPLICATE_DEFAULT_INSIDECONFIG_ANNOTATION_TESTOBJECTS_PACKAGE =
      "org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.insideconfig";

  // - Simple Dependency Injection Tests
  @Test
  public void appContext_properDirectoryPath_properObjectsInContext() {
    AppContext context = new AppContext(SIMPLE_DI_TESTOBJECTS_PACKAGE);
    assertThat(context.getContext().size()).isEqualTo(2);
  }

  @Test
  public void appContext_invalidDirectoryPath_noObjectsInContext() {
    AppContext context = new AppContext("invalid directory");
    assertThat(context.getContext().size()).isEqualTo(0);
  }

  @Test
  public void getItem_beanClass_properlyInjectedObject() {
    CustomApplicationContext context = new AppContext(SIMPLE_DI_TESTOBJECTS_PACKAGE);
    A obj = (A) context.getItem(A.class);
    assertThat(obj.getB()).isNotNull();
  }

  @Test
  public void getItem_beanClass_injectedObjectOfProperType() {
    CustomApplicationContext context = new AppContext(SIMPLE_DI_TESTOBJECTS_PACKAGE);
    A obj = (A) context.getItem(A.class);
    assertThat(obj.getB()).isInstanceOf(B.class);
  }

  @Test
  public void getItem_classToInjectNotInContext_properlyInjectedObject() {
    assertThatThrownBy(
            () -> new AppContext(SIMPLE_DI_TESTOBJECTS_CLASS_TO_INJECT_NOT_IN_CONTEXT_PACKAGE))
        .isInstanceOf(NoSuchBeanFoundException.class);
  }

  // - Simple Dependency Injection Tests ^^
  @Test
  public void getItem_ConfigClassWithDefaultAnnotation_properObjectInContext() {
    CustomApplicationContext context = new AppContext(PROPER_CONFIG_TESTOBJECTS_PACKAGE);
    var exampleObj = (ExampleObj) context.getItem(ExampleObj.class);
    var cObject = (ObjectC) context.getItem(ObjectC.class);
    assertAll(
        () -> assertThat(exampleObj).isNotNull(),
        () -> assertThat(exampleObj).isInstanceOf(ExampleObj.class),
        () -> assertThat(cObject).isNotNull(),
        () -> assertThat(cObject).isInstanceOf(ObjectC.class));
  }

  @Test
  public void getItem_ConfigClassNoDefaultAnnotation_duplicateBeansExceptionThrown() {
    assertThatThrownBy(() -> new AppContext(INVALID_CONFIG_TESTOBJECTS_PACKAGE))
        .isInstanceOf(DuplicateBeansFound.class);
  }

  @Test
  public void getItem_notAnnotatedObjectC2_noSuchBeanExceptionThrown() {
    AppContext context = new AppContext(PROPER_CONFIG_TESTOBJECTS2_PACKAGE);
    assertThatThrownBy(() -> context.getItem(ObjectC2.class))
        .isInstanceOf(NoSuchBeanFoundException.class);
  }

  @Test
  public void getItem_defaultAnnotatedBeanOutsideConfigClass_properlyInitializedBean() {
    AppContext context = new AppContext(DEFAULT_ANNOTATION_TESTOBJECT_PACKAGE);
    assertThat(context.getItem(DefaultTest.class)).isNotNull();
  }

  @Test
  public void getItem_duplicateBeanOutsideConfig_duplicateBeanExceptionThrown() {
    assertThatThrownBy(() -> new AppContext(NO_DEFAULT_ANNOTATION_TESTOBJECT_PACKAGE))
        .isInstanceOf(DuplicateBeansFound.class);
  }

  @Test
  public void getItem_getObjectByItsInterfaceNoDefaultAnnotation_duplicateBeanExceptionThrown() {
    var context = new AppContext(NO_DEFAULT_ANNOTATION_SAMEINTERFACE_TESTOBJECTS_PACKAGE);
    assertThatThrownBy(() -> context.getItem(MessageService.class))
        .isInstanceOf(DuplicateBeansFound.class);
  }

  @Test
  public void getItem_getObjectByItsInterface_properDefaultAnnotatedObject() {
    var context = new AppContext(DEFAULT_ANNOTATION_SAMEINTERFACE_TESTOBJECTS_PACKAGE);
    var result = context.getItem(MessageService2.class);
    assertThat(result).isInstanceOf(EmailService2.class);
  }

  @Test
  public void getItem_duplicateDefaultBeansInsideConfig_duplicateBeansExceptionThrown() {
    assertThatThrownBy(
            () -> new AppContext(DUPLICATE_DEFAULT_INSIDECONFIG_ANNOTATION_TESTOBJECTS_PACKAGE))
        .isInstanceOf(DuplicateBeansFound.class);
  }

  @Test
  public void getItem_duplicateDefaultBeansOutsideConfig_duplicateBeansExceptionThrown() {
    var context = new AppContext(DUPLICATE_DEFAULT_OUTSIDECONFIG_ANNOTATION_TESTOBJECTS_PACKAGE);
    assertThatThrownBy(() -> context.getItem(MessageService3.class))
        .isInstanceOf(DuplicateBeansFound.class);
  }

}
