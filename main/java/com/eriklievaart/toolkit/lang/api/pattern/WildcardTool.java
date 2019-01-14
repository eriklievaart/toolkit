package com.eriklievaart.toolkit.lang.api.pattern;

import com.eriklievaart.toolkit.lang.api.str.CharIterator;

/**
 * Performs basic wildcard matching on '?' (single character) and '*' (zero or more characters).
 *
 * @author Erik Lievaart
 */
public class WildcardTool {
	private static final char ANY_CHARACTER = '?';
	private static final char WILDCARD = '*';

	private WildcardTool() {
	}

	/**
	 * Matches an input String against a wildcard pattern and returns the result as a flag.
	 */
	public static boolean match(final String pattern, final String input) {
		return match(new CharIterator(pattern), new CharIterator(input));
	}

	private static boolean match(final CharIterator pattern, final CharIterator input) {
		while (pattern.hasNext()) {
			if (pattern.getLookahead() == WILDCARD) {
				return wildcardMatches(pattern, input);
			}
			if (!singleCharacterMatches(pattern.next(), input)) {
				return false;
			}
		}
		return !input.hasNext();
	}

	private static boolean wildcardMatches(final CharIterator pattern, final CharIterator input) {
		skipWildcards(pattern);
		if (!pattern.hasNext()) {
			return true;
		}
		while (input.hasNext()) {
			if (match(pattern.newCharIteratorFromHere(), input.newCharIteratorFromHere())) {
				return true;
			}
			input.next();
		}
		return false;
	}

	static boolean singleCharacterMatches(final char search, final CharIterator input) {
		if (!input.hasNext()) {
			return false;
		}
		if (search != input.next() && search != ANY_CHARACTER) {
			return false;
		}
		return true;
	}

	static void skipWildcards(final CharIterator iter) {
		while (iter.hasNext() && iter.getLookahead() == '*') {
			iter.next();
		}
	}
}
