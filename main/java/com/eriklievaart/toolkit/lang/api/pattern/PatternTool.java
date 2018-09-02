package com.eriklievaart.toolkit.lang.api.pattern;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;
import com.eriklievaart.toolkit.lang.api.concurrent.ThreadSafe;

/**
 * Utility class for working with {@link Pattern}'s. This class caches compiled regexes internally.
 *
 * @author Erik Lievaart
 */
@ThreadSafe
public class PatternTool {

	private static final int FIRST = 1;
	private static final int SECOND = 2;

	private PatternTool() {
	}

	/**
	 * Create a new {@link Pattern} from a regex and cache it.
	 */
	public static Pattern pattern(final String regex) {
		return Pattern.compile(regex, Pattern.DOTALL);
	}

	/**
	 * Create a new {@link Matcher} for a given regex and input {@link String}.
	 */
	private static Matcher matcher(final String regex, final String input) {
		return pattern(regex).matcher(input);
	}

	/**
	 * Find the first match of a regex in the supplied input String.
	 *
	 * @return the first match or <code>null</code> if none was found
	 */
	public static String find(final String regex, final String input) {
		Matcher matcher = matcher(regex, input);
		return matcher.find() ? matcher.group() : null;
	}

	/**
	 * Find all matches of the supplied regex in the input String.
	 */
	public static List<String> findAll(final String regex, final String input) {
		Matcher matcher = matcher(regex, input);
		List<String> all = NewCollection.list(NullPolicy.REJECT);
		while (matcher.find()) {
			all.add(matcher.group());
		}
		return all;
	}

	/**
	 * Matches the input against the regex and returns group 1.
	 *
	 * @return the group, or null if the regex does not match.
	 */
	public static String getGroup1(final String regex, final String input) {
		return getGroup(regex, input, FIRST);
	}

	/**
	 * Matches the input against the regex and returns group 2.
	 *
	 * @return the group, or null if the regex does not match.
	 */
	public static String getGroup2(final String regex, final String input) {
		return getGroup(regex, input, SECOND);
	}

	/**
	 * Matches the input against the regex and returns the requested group.
	 *
	 * @return the group, or null if the regex does not match.
	 */
	public static String getGroup(final String regex, final String input, final int group) {
		Matcher matcher = matcher(regex, input);
		if (!matcher.matches()) {
			return null;
		}
		return matcher.group(group);
	}

	/**
	 * Matches the input against the regex and returns all groups found starting with group 0 (the whole input).
	 */
	public static List<String> getGroups(final String regex, final String input) {
		List<String> groups = NewCollection.list(NullPolicy.ACCEPT);
		Matcher matcher = matcher(regex, input);
		if (!matcher.matches()) {
			return groups;
		}
		int count = matcher.groupCount();
		for (int i = 0; i <= count; i++) {
			groups.add(matcher.group(i));
		}
		return groups;
	}

	/**
	 * Split the supplied input against the supplied regex.
	 *
	 * @see Pattern#split(CharSequence)
	 */
	public static String[] split(final String regex, final String input) {
		return pattern(regex).split(input);
	}

	/**
	 * Tests if the input matches the regex, and returns the result as a flag.
	 *
	 * @see Matcher#matches()
	 */
	public static boolean matches(final String regex, final String input) {
		return matcher(regex, input).matches();
	}

	/**
	 * Tests if a regular expression can be compiled (in other words, if it is a valid Java regex). This method does not
	 * cache the Pattern, since this method does not make sense for static regexes.
	 */
	public static boolean isCompilable(final String regex) {
		try {
			Pattern.compile(regex);
			return true;
		} catch (PatternSyntaxException pse) {
			return false;
		}
	}

	/**
	 * Replace all matches of the regex with the replacement String in the input String.
	 *
	 * @see Matcher#replaceAll(String)
	 */
	public static String replaceAll(final String regex, final String replace, final String input) {
		return matcher(regex, input).replaceAll(replace);
	}

}
