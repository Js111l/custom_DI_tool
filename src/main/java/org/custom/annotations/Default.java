package org.custom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
//Spring's @Primary
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
}
