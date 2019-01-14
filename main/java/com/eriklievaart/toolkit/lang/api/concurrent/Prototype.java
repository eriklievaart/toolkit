package com.eriklievaart.toolkit.lang.api.concurrent;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a class as a non Thread safe prototype. Create a new instance for every usage.
 *
 * @author Erik Lievaart
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Prototype {
}
