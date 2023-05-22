package com.eriklievaart.toolkit.lang.api.str;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.mock.BombSquad;

public class CharIteratorU {

	@Test
	public void newCharIteratorFromHere() {
		CharIterator testable = new CharIterator("123");
		testable.next();

		CharIterator copy = testable.newCharIteratorFromHere();
		copy.next();

		Check.isEqual(testable.toString(), "23");
		Check.isEqual(copy.toString(), "3");
	}

	@Test
	public void newCharIteratorFromHereBorderCaseEndOfInput() {
		CharIterator testable = new CharIterator("1");
		testable.next();
		CharIterator copy = testable.newCharIteratorFromHere();
		Check.isEqual(copy.toString(), "");
	}

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
	public void getLookbehind() {
		CharIterator iter = new CharIterator("a");
		Check.isEqual(iter.next(), 'a');
		Check.isEqual(iter.getLookbehind(), 'a');
	}

	@Test
	public void hasLookbehind() {
		CharIterator iter = new CharIterator("ab");
		Check.isFalse(iter.hasLookbehind('a'));
		Check.isFalse(iter.hasLookbehind('b'));
		Check.isFalse(iter.hasLookbehind('a', 'b'));
		Check.isFalse(iter.hasLookbehind('b', 'a'));

		iter.next();
		Check.isTrue(iter.hasLookbehind('a'));
		Check.isFalse(iter.hasLookbehind('b'));
		Check.isTrue(iter.hasLookbehind('a', 'b'));
		Check.isTrue(iter.hasLookbehind('b', 'a'));

		iter.next();
		Check.isFalse(iter.hasLookbehind('a'));
		Check.isTrue(iter.hasLookbehind('b'));
		Check.isTrue(iter.hasLookbehind('a', 'b'));
		Check.isTrue(iter.hasLookbehind('b', 'a'));
	}

	@Test
	public void hasLookahead() {
		CharIterator iter = new CharIterator("ab");
		Check.isTrue(iter.hasLookahead('a'));
		Check.isFalse(iter.hasLookahead('b'));
		Check.isTrue(iter.hasLookahead('a', 'b'));
		Check.isTrue(iter.hasLookahead('b', 'a'));

		iter.next();
		Check.isFalse(iter.hasLookahead('a'));
		Check.isTrue(iter.hasLookahead('b'));
		Check.isTrue(iter.hasLookahead('a', 'b'));
		Check.isTrue(iter.hasLookahead('b', 'a'));

		iter.next();
		Check.isFalse(iter.hasLookahead('a'));
		Check.isFalse(iter.hasLookahead('b'));
		Check.isFalse(iter.hasLookahead('a', 'b'));
		Check.isFalse(iter.hasLookahead('b', 'a'));
	}

	@Test
	public void hasLookaheadNotIn() {
		CharIterator iter = new CharIterator("ab");
		Check.isFalse(iter.hasLookaheadNotIn('a'));
		Check.isTrue(iter.hasLookaheadNotIn('b'));
		Check.isFalse(iter.hasLookaheadNotIn('a', 'b'));
		Check.isFalse(iter.hasLookaheadNotIn('b', 'a'));

		iter.next();
		Check.isTrue(iter.hasLookaheadNotIn('a'));
		Check.isFalse(iter.hasLookaheadNotIn('b'));
		Check.isFalse(iter.hasLookaheadNotIn('a', 'b'));
		Check.isFalse(iter.hasLookaheadNotIn('b', 'a'));

		iter.next();
		Check.isFalse(iter.hasLookaheadNotIn('a'));
		Check.isFalse(iter.hasLookaheadNotIn('b'));
		Check.isFalse(iter.hasLookaheadNotIn('a', 'b'));
		Check.isFalse(iter.hasLookaheadNotIn('b', 'a'));
	}

	@Test(expected = AssertionException.class)
	public void getLookbackFailure() {
		CharIterator iter = new CharIterator("a");
		iter.getLookbehind();
	}

	@Test(expected = AssertionException.class)
	public void nextEmpty() {
		new CharIterator("", 0, 0).next();
	}

	@Test
	public void next() {
		Check.isEqual(new CharIterator("a").next(), 'a');
	}

	@Test(expected = AssertionException.class)
	public void nextFailure() {
		CharIterator iter = new CharIterator("a");
		iter.next();
		iter.next();
	}

	@Test
	public void appendIfLookahead() {
		StringBuilderWrapper builder = new StringBuilderWrapper();

		CharIterator iter = new CharIterator("abc");
		iter.appendIfLookahead('a', builder);
		iter.appendIfLookahead('b', builder);
		iter.appendIfLookahead('d', builder);

		Check.isEqual(builder.toString(), "ab");
		Check.isTrue(iter.hasLookahead('c'));
	}

	@Test
	public void appendUntilLookahead() {
		StringBuilderWrapper builder = new StringBuilderWrapper();

		CharIterator iter = new CharIterator("ab@cd");
		iter.appendUntilLookahead('@', builder);
		Check.isEqual(builder.toString(), "ab");
		Check.isTrue(iter.hasLookahead('@'));
	}

	@Test
	public void appendUpToRequired() {
		StringBuilderWrapper builder = new StringBuilderWrapper();

		CharIterator iter = new CharIterator("ab@cd");
		iter.appendUpToRequired('@', builder);
		Check.isEqual(builder.toString(), "ab@");
		Check.isTrue(iter.hasLookahead('c'));
	}

	@Test
	public void appendUpToRequiredFail() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		CharIterator iter = new CharIterator("abcd");

		BombSquad.diffuse("expecting: @", () -> {
			iter.appendUpToRequired('@', builder);
		});
	}

	@Test
	public void previous() {
		CharIterator iter = new CharIterator("a");
		Check.isEqual(iter.next(), 'a');
		Check.isEqual(iter.previous(), 'a');
	}

	@Test(expected = AssertionException.class)
	public void previousFailure() {
		CharIterator iter = new CharIterator("a");
		iter.previous();
	}

	@Test
	public void nextLine() {
		CharIterator iter = new CharIterator("abc\ndef");
		Check.isEqual(iter.nextLine(), "abc\n");
		Check.isEqual(iter.nextLine(), "def");
	}

	@Test
	public void hasNextEmpty() {
		Check.isFalse(new CharIterator("").hasNext());
	}

	@Test
	public void findFirst() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		CharIterator iterator = new CharIterator("123456789");

		Check.isTrue(iterator.find("12", builder));
		Check.isEqual(builder.toString(), "");
		Check.isEqual(iterator.toString(), "123456789");
	}

	@Test
	public void findMiddle() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		CharIterator iterator = new CharIterator("1234512345");

		Check.isTrue(iterator.find("5", builder));
		Check.isEqual(builder.toString(), "1234");
		Check.isEqual(iterator.toString(), "512345");
	}

	@Test
	public void findLast() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		CharIterator iterator = new CharIterator("123456789");

		Check.isTrue(iterator.find("89", builder));
		Check.isEqual(builder.toString(), "1234567");
		Check.isEqual(iterator.toString(), "89");
	}

	@Test
	public void findNone() {
		CharIterator iterator = new CharIterator("123456789");
		Check.isFalse(iterator.find("98", new StringBuilderWrapper()));
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