package org.custom.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.custom.core.annotations.Item;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Item
public @interface RestController {}
