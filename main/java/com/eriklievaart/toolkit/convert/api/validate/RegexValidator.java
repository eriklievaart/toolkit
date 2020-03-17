package com.eriklievaart.toolkit.convert.api.validate;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Validator;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;

/**
 * Validator that validates an input String using the specified regular expression.
 *
 * @author Erik Lievaart
 */
public class RegexValidator implements Validator {

	private final String regex;
	private final String message;

	/**
	 * Constructor.
	 *
	 * @param regex
	 *            Validates the input String.
	 */
	public RegexValidator(final String regex) {
		this(regex, "Invalid argument: % for regex: %");
	}

	/**
	 *
	 * @param regex
	 *            Validates the input String.
	 * @param message
	 *            Custom message to show when the validation fails. Contains 2 percentile '%' placeholders that denote
	 *            the argument and the regular expression.
	 */
	public RegexValidator(final String regex, final String message) {
		this.regex = regex;
		this.message = message;
		Check.isTrue(PatternTool.isCompilable(regex), "Pattern does not compile!: %", regex);
	}

	@Override
	public boolean isValid(final String value) {
		return value == null ? false : PatternTool.matches(regex, value);
	}

	@Override
	public void check(final String value) throws ConversionException {
		ConversionException.unless(isValid(value), message, value, regex);
	}
}