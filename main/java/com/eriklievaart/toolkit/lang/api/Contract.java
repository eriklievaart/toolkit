package com.eriklievaart.toolkit.lang.api;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to mark a class or method as part of a contract. Marking them as part of the contract signals that
 * changes should be carefully considered. Code quality checks might be skipped for such classes or methods.
 * 
 * @author Erik Lievaart
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Contract {

}
