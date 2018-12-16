package com.eriklievaart.toolkit.lang.api.str;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.CharIterator;

public class CharIteratorU {

	@Test(expected = AssertionException.class)
	public void constructorEmptyFail() {
		new CharIterator("", 0, 0);
	}

	@Test
	public void constructorEmptySuccess() {
		new CharIterator("", 0, -1);
	}

	@Test(expected = AssertionException.class)
	public void constructorEndError() {
		new CharIterator("a", 0, 1);
	}

	@Test(expected = AssertionException.class)
	public void constructorStartError() {
		new CharIterator("", 1, 0);
	}

	@Test(expected = AssertionException.class)
	public void getNextEmpty() {
		new CharIterator("", 0, 0).next();
	}

	@Test
	public void getNext() {
		Check.isEqual(new CharIterator("a").next(), 'a');
	}

	@Test(expected = AssertionException.class)
	public void getNextFailure() {
		CharIterator iter = new CharIterator("a");
		iter.next();
		iter.next();
	}

	@Test
	public void getLookahead() {
		CharIterator iter = new CharIterator("a");
		Check.isEqual(iter.getLookahead(), 'a');
		Check.isEqual(iter.next(), 'a');
	}

	@Test(expected = AssertionException.class)
	public void getLookaheadFailure() {
		CharIterator iter = new CharIterator("");
		iter.getLookahead();
	}

	@Test
	public void getLookback() {
		CharIterator iter = new CharIterator("a");
		Check.isEqual(iter.next(), 'a');
		Check.isEqual(iter.getLookbehind(), 'a');
	}

	@Test(expected = AssertionException.class)
	public void getLookbackFailure() {
		CharIterator iter = new CharIterator("a");
		iter.getLookbehind();
	}

	@Test
	public void getPrevious() {
		CharIterator iter = new CharIterator("a");
		Check.isEqual(iter.next(), 'a');
		Check.isEqual(iter.previous(), 'a');
	}

	@Test(expected = AssertionException.class)
	public void getPreviousFailure() {
		CharIterator iter = new CharIterator("a");
		iter.previous();
	}

	@Test
	public void hasNextEmpty() {
		Check.isFalse(new CharIterator("").hasNext());
	}

	@Test
	public void hasNext() {
		CharIterator iter = new CharIterator("a");
		Check.isTrue(iter.hasNext());
		iter.next();
		Check.isFalse(iter.hasNext());
	}

	@Test
	public void hasPreviousEmpty() {
		Check.isFalse(new CharIterator("").hasPrevious());
	}

	@Test
	public void hasPrevious() {
		CharIterator iter = new CharIterator("a");
		Check.isFalse(iter.hasPrevious());
		iter.next();
		Check.isTrue(iter.hasPrevious());
	}
}
