package com.eriklievaart.toolkit.convert.api.validate;

import com.eriklievaart.toolkit.convert.api.Validator;

/**
 * Validator that always returns true.
 *
 * @author Erik Lievaart
 */
public class AlwaysValidate implements Validator {

	/**
	 * returns true.
	 */
	@Override
	public boolean isValid(final String value) {
		return true;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void check(final String string) {
	}
}