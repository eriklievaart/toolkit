package com.eriklievaart.toolkit.lang.api.str;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eriklievaart.toolkit.lang.api.collection.MapTool;

public class TokenReplacer {

	/**
	 * Replaces tokens of the form \@name\@. Tokens are not case sensitive.
	 */
	public static String sub(String text, Map<String, String> replacements) {
		Map<String, String> lower = MapTool.mapKeys(replacements, k -> k.toLowerCase());
		Pattern pattern = Pattern.compile("@([^@]++)@");
		Matcher matcher = pattern.matcher(text);
		StringBuilder builder = new StringBuilder();

		while (matcher.find()) {
			String replacement = lower.get(matcher.group(1).toLowerCase());
			if (replacement != null) {
				matcher.appendReplacement(builder, "");
				builder.append(replacement);
			}
		}
		matcher.appendTail(builder);
		return builder.toString();
	}
}
