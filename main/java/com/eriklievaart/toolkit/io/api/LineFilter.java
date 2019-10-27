package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class LineFilter {

	private final String[] lines;
	private List<Predicate<String>> predicates = NewCollection.list();
	private List<Function<String, String>> functions = NewCollection.list();
	private String eof = null;

	public LineFilter(String data) {
		Check.notNull(data);
		lines = Str.splitLines(data);
	}

	public LineFilter(List<String> data) {
		Check.notNull(data);
		lines = data.toArray(new String[] {});
	}

	public LineFilter(File file) throws IOException {
		this(FileTool.toString(file));
	}

	public LineFilter(InputStream is) throws IOException {
		this(StreamTool.readLines(is));
	}

	public List<String> list() {
		List<String> list = NewCollection.list();
		iterate((number, text) -> {
			list.add(text);
		});
		return list;
	}

	public void iterate(BiConsumer<Integer, String> consumer) {
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (eof != null && line.trim().equalsIgnoreCase(eof.trim())) {
				return;
			}
			if (predicates.stream().noneMatch(p -> p.test(line))) {
				consumer.accept(i + 1, applyFunctions(line));
			}
		}
	}

	private String applyFunctions(String line) {
		String result = line;
		for (Function<String, String> function : functions) {
			result = function.apply(result);
		}
		Check.notNull(result, "line transformed into <null>! $", line);
		return result;
	}

	public LineFilter dropEmpty() {
		predicates.add(s -> Str.isEmpty(s));
		return this;
	}

	public LineFilter dropBlank() {
		predicates.add(s -> Str.isBlank(s));
		return this;
	}

	public LineFilter dropHash() {
		predicates.add(s -> s.trim().startsWith("#"));
		return this;
	}

	public LineFilter drop(Predicate<String> predicate) {
		Check.notNull(predicate);
		predicates.add(predicate);
		return this;
	}

	public LineFilter dropRegex(String regex) {
		Check.notNull(regex);
		predicates.add(s -> s.matches(regex));
		return this;
	}

	public LineFilter keep(Predicate<String> predicate) {
		Check.notNull(predicate);
		predicates.add(predicate.negate());
		return this;
	}

	public LineFilter keepRegex(String regex) {
		Check.notNull(regex);
		predicates.add(s -> !s.matches(regex));
		return this;
	}

	public LineFilter regexReplaceAll(String regex, String replacement) {
		functions.add(in -> in.replaceAll(regex, replacement));
		return this;
	}

	public LineFilter eof() {
		eof = "@EOF@";
		return this;
	}

	public LineFilter eof(String line) {
		eof = line;
		return this;
	}

	public LineFilter trim() {
		functions.add(String::trim);
		return this;
	}

	public LineFilter map(Function<String, String> function) {
		functions.add(function);
		return this;
	}
}
