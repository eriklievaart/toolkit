package com.eriklievaart.toolkit.lang.api.pattern;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.pattern.WildcardTool;
import com.eriklievaart.toolkit.lang.api.str.CharIterator;

public class WildcardToolU {

	@Test
	public void matchExact() {
		Check.isTrue(WildcardTool.match("exact", "exact"));
	}

	@Test
	public void matchShort() {
		Check.isFalse(WildcardTool.match("short", "shor"));
	}

	@Test
	public void matchLong() {
		Check.isFalse(WildcardTool.match("long", "longer"));
	}

	@Test
	public void matchEmpty() {
		Check.isFalse(WildcardTool.match("s", ""));
	}

	@Test
	public void matchExactAnyCharacter() {
		Check.isTrue(WildcardTool.match("exa?t", "exact"));
	}

	@Test
	public void matchShortAnyCharacter() {
		Check.isFalse(WildcardTool.match("sho?t", "shor"));
	}

	@Test
	public void matchLongAnyCharacter() {
		Check.isFalse(WildcardTool.match("lo?g", "longer"));
	}

	@Test
	public void matchEmptyAnyCharacter() {
		Check.isFalse(WildcardTool.match("?", ""));
	}

	@Test
	public void matchWildcardOnly() {
		Check.isTrue(WildcardTool.match("*", "some arbitrary String"));
	}

	@Test
	public void matchWildcardStart() {
		Check.isTrue(WildcardTool.match("*t", "start"));
	}

	@Test
	public void matchWildcardEnd() {
		Check.isTrue(WildcardTool.match("e*", "end"));
	}

	@Test
	public void matchWildcardMiddle() {
		Check.isTrue(WildcardTool.match("m*e", "middle"));
	}

	@Test
	public void matchWildcardImpossible() {
		Check.isFalse(WildcardTool.match("*impossible", "If you can't do it, no one can!"));
	}

	@Test
	public void matchWildcardTooManyOccurences() {
		Check.isFalse(WildcardTool.match("*so*so*", "It was like, soooo good!"));
	}

	@Test
	public void matchWildcardRedundant() {
		Check.isTrue(WildcardTool.match("*re***dun**dant*", "redundant"));
	}

	@Test
	public void matchComplex() {
		Check.isTrue(WildcardTool.match("*re***d?n**?an?*", "redundant"));
	}

	@Test
	public void skipWildcardsEmpty() {
		CharIterator iterator = new CharIterator("*");

		WildcardTool.skipWildcards(iterator);
		Check.isEqual(iterator.getLookbehind(), '*');
		Check.isFalse(iterator.hasNext());
	}

	@Test
	public void skipWildcardsNone() {
		CharIterator iterator = new CharIterator("none");

		WildcardTool.skipWildcards(iterator);
		Check.isEqual(iterator.next(), 'n');
	}

	@Test
	public void skipWildcardsSingle() {
		CharIterator iterator = new CharIterator("*single");

		WildcardTool.skipWildcards(iterator);
		Check.isEqual(iterator.next(), 's');
	}

	@Test
	public void skipWildcardsMultiple() {
		CharIterator iterator = new CharIterator("***multiple");

		WildcardTool.skipWildcards(iterator);
		Check.isEqual(iterator.next(), 'm');
	}

	@Test
	public void singleCharacterMatches() {
		Check.isTrue(WildcardTool.singleCharacterMatches('e', new CharIterator("e")));
	}

	@Test
	public void singleCharacterMatchesOtherCharacter() {
		Check.isFalse(WildcardTool.singleCharacterMatches('o', new CharIterator("e")));
	}

	@Test
	public void singleCharacterMatchesEmpty() {
		Check.isFalse(WildcardTool.singleCharacterMatches('e', new CharIterator("")));
	}

	@Test
	public void singleCharacterAnyMatches() {
		Check.isTrue(WildcardTool.singleCharacterMatches('?', new CharIterator("a")));
	}

	@Test
	public void singleCharacterAnyMatchesEmpty() {
		Check.isFalse(WildcardTool.singleCharacterMatches('?', new CharIterator("")));
	}

}
