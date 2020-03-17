package com.eriklievaart.toolkit.io.api;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class LineFilterU {

	@Test
	public void dropEof() {
		List<String> lines = new LineFilter("\n \n#\n@eof@\nbla").eof().list();
		Assertions.assertThat(lines).containsExactly("", " ", "#");
	}

	@Test
	public void dropEofCustom() {
		List<String> lines = new LineFilter("\n \n@eof@\n#\nbla").eof("#").list();
		Assertions.assertThat(lines).containsExactly("", " ", "@eof@");
	}

	@Test
	public void dropNone() {
		List<String> lines = new LineFilter("\n \n#\nbla").list();
		Assertions.assertThat(lines).containsExactly("", " ", "#", "bla");
	}

	@Test
	public void dropEmpty() {
		List<String> lines = new LineFilter("\n \n#\n\nbla").dropEmpty().list();
		Assertions.assertThat(lines).containsExactly(" ", "#", "bla");
	}

	@Test
	public void dropBlank() {
		List<String> lines = new LineFilter("\n \n#\n\nbla").dropBlank().list();
		Assertions.assertThat(lines).containsExactly("#", "bla");
	}

	@Test
	public void dropHash() {
		List<String> lines = new LineFilter("\n \n#\nbla").dropHash().list();
		Assertions.assertThat(lines).containsExactly("", " ", "bla");
	}

	@Test
	public void dropLambda() {
		List<String> lines = new LineFilter("\n \n#\nbla").drop((l) -> l.matches("[a-z]++")).list();
		Assertions.assertThat(lines).containsExactly("", " ", "#");
	}

	@Test
	public void dropRegex() {
		List<String> lines = new LineFilter("\n \n#\nbla").dropRegex("[a-z]++").list();
		Assertions.assertThat(lines).containsExactly("", " ", "#");
	}

	@Test
	public void keepLambda() {
		List<String> lines = new LineFilter("\n \n#\nbla").keep((l) -> l.matches("[a-z]++")).list();
		Assertions.assertThat(lines).containsExactly("bla");
	}

	@Test
	public void keepRegex() {
		List<String> lines = new LineFilter("\n \n#\nbla").keepRegex("[a-z]++").list();
		Assertions.assertThat(lines).containsExactly("bla");
	}

	@Test
	public void trim() {
		List<String> lines = new LineFilter("   a    ").trim().list();
		Assertions.assertThat(lines).containsExactly("a");
	}

	@Test
	public void map() {
		List<String> lines = new LineFilter("cd").map(e -> "ab" + e).list();
		Assertions.assertThat(lines).containsExactly("abcd");
	}

	@Test
	public void regexReplaceAll() {
		List<String> lines = new LineFilter("red").regexReplaceAll("e", "a").list();
		Assertions.assertThat(lines).containsExactly("rad");
	}

	@Test
	public void iterate() {
		List<String> actual = NewCollection.list();
		new LineFilter("one\n \nthree").dropBlank().iterate((index, line) -> {
			actual.add(index + line);
		});
		Assertions.assertThat(actual).containsExactly("1one", "3three");
	}
}