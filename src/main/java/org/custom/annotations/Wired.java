package org.custom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Spring's @AutoWired
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Wired {

}
