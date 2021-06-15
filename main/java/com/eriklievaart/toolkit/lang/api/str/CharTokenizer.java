package com.eriklievaart.toolkit.lang.api.str;

import java.util.Optional;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class CharTokenizer {

	private final CharIterator iterator;

	public CharTokenizer(String input) {
		this.iterator = new CharIterator(input);
	}

	public char nextCharacter() {
		return iterator.next();
	}

	public Optional<String> nextTokenUntil(char c) {
		return nextTokenUntilAny(c);
	}

	public Optional<String> nextTokenUntilAny(char... stop) {
		Check.isTrue(stop.length > 0, "no characters passed!");
		StringBuilder builder = new StringBuilder();

		while (iterator.hasNext()) {
			char next = iterator.getLookahead();
			if (containsAny(stop, next)) {
				break;
			}
			builder.append(iterator.next());
		}
		if (builder.length() == 0) {
			return Optional.empty();
		}
		return Optional.of(builder.toString());
	}

	public int skipAndCount(char c) {
		int count = 0;
		while (iterator.hasNext() && iterator.getLookahead() == c) {
			iterator.next();
			count++;
		}
		return count;
	}

	private boolean containsAny(char[] any, char c) {
		for (char a : any) {
			if (c == a) {
				return true;
			}
		}
		return false;
	}

	public Optional<String> nextNonWhitespaceToken() {
		return nextTokenUntilAny(' ', '\t');
	}

	public Optional<String> nextNonNewLineToken() {
		return nextTokenUntilAny('\r', '\n');
	}

	public void skipWhitespace() {
		while (iterator.hasNext()) {
			if (iterator.getLookahead() == ' ' || iterator.getLookahead() == '\t') {
				iterator.next();
			} else {
				break;
			}
		}
	}

	public String getDebugLine() {
		return iterator.getDebugLine();
	}

	public boolean isBlank() {
		return Str.isBlank(iterator.newCharIteratorFromHere().toString());
	}

	@Override
	public String toString() {
		return iterator.toString();
	}
}
