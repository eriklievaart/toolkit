package com.eriklievaart.toolkit.lang.api.str;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.lang.api.collection.SetTool;
import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;

/**
 * This class is responsible for converting String with or without escape sequences to their counterpart. For example:
 * &quot;has\swhitespace&quot; could be converted to &quot;has whitespace&quot; and back. When a StringEscape is created
 * the escape character is specified along with a mapping between control characters and string characters.
 *
 * The example above in code:
 *
 * <pre>
 * StringEscape string = new StringEscape(MapUtils.of('s', ' '), '\\');
 * String escaped = string.escape(&quot;has whitespace&quot;); // result: &quot;has\swhitespace&quot;
 * String unescaped = string.unescape(&quot;has\\swhitespace&quot;); // result: &quot;has whitespace&quot;
 * </pre>
 *
 * About the terminology used:
 *
 * <pre>
 * String character = A character that is not allowed to occur in the input and therefore requires an escape sequence.
 * Escape character = The character used as a prefix for escaping String characters.
 * Control character = Follows an escape character. Control characters uniquely identify String characters.
 * Escape sequence = An Escape character with a control character. This combination be converted to a String character.
 * </pre>
 *
 * This class is not without limitations. It only understands single character escapes. It is not capable of detecting
 * starting and ending delimiters for quoted Strings and such.
 *
 * @author Erik Lievaart
 */
@Immutable
public class StringEscape {

	private final char escape;
	private final Map<Character, Character> escapeMap;
	private final Map<Character, Character> unescapeMap;
	private final Set<Character> controlCharacters;
	private final Set<Character> stringCharacters;

	/**
	 * Create a StringEscape instance that uses the default escape character '\'.
	 *
	 * @param mapping
	 *            Defines a mapping from control characters to their String character. For instance, Map('c' => ':')
	 *            indicates that colons should be escaped with "\c".
	 */
	public StringEscape(final Map<Character, Character> mapping) {
		this(mapping, '\\');
	}

	/**
	 * Create a StringEscape instance that uses the specified escape character.
	 *
	 * @param mapping
	 *            Defines a mapping from control characters to their String character. For instance, Map('c' => ':')
	 *            indicates that colons should be escaped with "\c" (if '\' is the escape character).
	 * @param escape
	 *            escape character to use.
	 */
	public StringEscape(final Map<Character, Character> mapping, final char escape) {
		this.escape = escape;
		this.unescapeMap = MapTool.unmodifiableCopy(mapping);
		this.escapeMap = Collections.unmodifiableMap(MapTool.reverse(mapping));
		controlCharacters = SetTool.unmodifiableCopy(mapping.keySet());
		stringCharacters = SetTool.unmodifiableCopy(mapping.values());
	}

	/**
	 * Escapes all occurrences of string characters in a String.
	 *
	 * @param input
	 *            String to escape.
	 * @return a String with all escape characters replaced with the escape character and a control character.
	 */
	public String escape(final String input) {
		StringBuilder builder = new StringBuilder(input);
		escape(builder);
		return builder.toString();
	}

	public StringBuilder escape(final StringBuilder builder) {
		for (int i = 0; i < builder.length(); i++) {
			if (isStringCharacter(builder.charAt(i))) {
				builder.replace(i, i + 1, escape(builder.charAt(i)));
			}
		}
		return builder;
	}

	private String escape(final char c) {
		CheckCollection.isPresent(stringCharacters, c);
		return new String(new char[] { escape, escapeMap.get(c) });
	}

	private boolean isStringCharacter(final char c) {
		return stringCharacters.contains(c);
	}

	public String[] unescape(final String[] arguments) {
		String[] out = new String[arguments.length];

		for (int i = 0; i < arguments.length; i++) {
			out[i] = unescape(arguments[i]);
		}
		return out;
	}

	/**
	 * Replaces all escape sequences in a String with the string character. All escape sequences in the String are
	 * assumed to be valid. For invalid escape sequences the result is unspecified, an Exception might be thrown.
	 *
	 * @param input
	 *            String that contains escape characters.
	 * @return a String with all escape sequences replaced with the string character.
	 */
	public String unescape(final String input) {
		CharIterator iter = new CharIterator(input);
		StringBuilder builder = new StringBuilder();

		while (iter.hasNext()) {
			if (iter.next() != escape) {
				builder.append(iter.getLookbehind());
			} else {
				Check.isTrue(iter.hasNext(), "Trailing escape character: %", input);
				builder.append(unescape(iter.next()));
			}
		}
		return builder.toString();
	}

	private Character unescape(final char c) {
		CheckCollection.isPresent(controlCharacters, c, "Invalid escape: %, valid escapes: %", c, controlCharacters);
		return unescapeMap.get(c);
	}

	public boolean hasInvalidEscapeSequence(final String input) {
		for (int i = 0; i < input.length() - 1; i++) {
			if (input.charAt(i) == escape && !controlCharacters.contains(input.charAt(i + 1))) {
				return true;
			}
		}
		return input.length() > 0 && input.charAt(input.length() - 1) == escape;
	}
}
