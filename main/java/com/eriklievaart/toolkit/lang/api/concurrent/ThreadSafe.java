package com.eriklievaart.toolkit.lang.api.concurrent;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a class as Thread safe. The class synchronizes internally.
 * 
 * @author Erik Lievaart
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ThreadSafe {
}
