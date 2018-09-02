package com.eriklievaart.toolkit.convert.api.validate;

/**
 * Validator that verifies that the value passed is a valid Map. Verifies that every key has a value.
 * 
 * @author Erik Lievaart
 */
public class MapValidator extends RegexValidator {

	public MapValidator() {
		super("[^#=]++=[^#=]++(?:\\#[^#=]++=[^#=]++)*+", "Not a valid value: %. Does every key have a value?");
	}

}
