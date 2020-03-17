package com.eriklievaart.toolkit.lang.api.str;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.concurrent.RaceCondition;

/**
 * This class can be used to iterate over any CharSequence. It can be re-used for different Strings. This class is not
 * Thread safe.
 *
 * @author Erik Lievaart
 */
@RaceCondition("This class was not intended to be shared among threads.")
public class CharIterator {

	private final CharSequence chars;
	private final int start;
	private final int last;
	private int index;

	public CharIterator(final CharSequence chars) {
		this(chars, 0);
	}

	public CharIterator(final CharSequence chars, final int start) {
		this(chars, start, chars == null ? 0 : chars.length() - 1);
	}

	/**
	 * Construct a new CharIterator.
	 *
	 * @param chars
	 *            CharSequence to iterate over.
	 * @param start
	 *            index to start iterating.
	 * @param last
	 *            Last index to iterate over. Typically: chars.length() - 1
	 */
	public CharIterator(final CharSequence chars, final int start, final int last) {
		this.chars = chars;
		this.start = start;
		this.last = last;
		index = start;

		checkAssertionExceptions();
	}

	public CharIterator newCharIteratorFromHere() {
		return new CharIterator(chars, index);
	}

	private void checkAssertionExceptions() {
		Check.notNull(chars);
		Check.isTrue(last == -1 || last < chars.length(), "Last index $ does not exist in CharSequence %", last, chars);
		Check.isTrue(last == -1 || index <= last + 1, "Start is beyond content: $ > $ in %", start, last, chars);
	}

	public char next() {
		Check.isTrue(hasNext(), "No more elements! Call hasNext() before calling getNext()");
		return chars.charAt(index++);
	}

	public void skip() {
		index++;
	}

	public void skip(int s) {
		index += s;
	}

	public char getLookahead() {
		Check.isTrue(hasNext(), "No more elements! Call hasNext() before calling getLookahead()");
		return chars.charAt(index);
	}

	public char getLookbehind() {
		Check.isTrue(index > start, "No lookback available at index %", index);
		return chars.charAt(index - 1);
	}

	public char previous() {
		Check.isTrue(hasPrevious(), "No more elements! Call hasPrevious() before calling getPrevious()");
		return chars.charAt(--index);
	}

	public boolean hasNext() {
		return index <= last;
	}

	public boolean hasPrevious() {
		return index > start;
	}

	@Override
	public String toString() {
		return chars.toString().substring(index);
	}

	public int length() {
		return chars.length() - index;
	}
}