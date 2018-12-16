package com.eriklievaart.toolkit.lang.api.concurrent;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Classes or methods annotated with this annotation are NOT Thread safe.
 *
 * @author Erik Lievaart
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RaceCondition {

	/**
	 * The reason this class is not ThreadSafe.
	 */
	String value();
}
