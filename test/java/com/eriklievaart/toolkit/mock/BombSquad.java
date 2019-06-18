package com.eriklievaart.toolkit.mock;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class BombSquad {

	/**
	 * @param consumer
	 *            pass the Exception to this consumer for additional checks if it occurs
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Exception> void diffuse(Class<E> expected, Bomb bomb, Consumer<E> consumer) {
		boolean exploded = false;
		try {
			bomb.explode();

		} catch (Exception e) {
			exploded = true;
			if (!expected.isInstance(e)) {
				throw new FormattedException("% not instanceof %; $", e, e.getClass(), expected, e.getMessage());
			}
			consumer.accept((E) e);
		}
		Check.isTrue(exploded, "$ expected", expected);
	}

	public static <E extends Exception> void diffuse(Class<E> expected, Bomb bomb) {
		diffuse(expected, bomb, e -> {
		});
	}

	public static <E extends Exception> void diffuse(Class<E> expected, String message, Bomb bomb) {
		diffuse(expected, bomb, e -> {
			if (e.getMessage() == null) {
				Check.isTrue(message == null, "Looking for %, but message is <null>", message);
				return;
			}
			if (!e.getMessage().toLowerCase().contains(message.toLowerCase())) {
				throw new FormattedException("message % does not contain %", e, e.getMessage(), message);
			}
		});
	}

	public static <E extends Exception> void diffuse(String message, Bomb bomb) {
		diffuse(Exception.class, message, bomb);
	}

	public static <E extends Exception> void diffuse(Class<E> expected, List<String> messages, Bomb bomb) {
		diffuse(expected, bomb, e -> {
			if (e.getMessage() == null) {
				throw new FormattedException("Looking for $, but message is <null>", messages);
			}
			for (String message : messages) {
				Check.isTrue(e.getMessage().contains(message), "message % does not contain %", e.getMessage(), message);
			}
		});
	}

	public static <E extends Exception> void diffuse(List<String> messages, Bomb bomb) {
		diffuse(Exception.class, messages, bomb);
	}

	public static <E extends Exception> void diffuseRegex(Class<E> expected, String regex, Bomb bomb) {
		diffuse(expected, bomb, e -> {
			if (e.getMessage() == null) {
				throw new FormattedException("Looking for %, but message is <null>", regex);
			}
			if (!Pattern.compile(regex.toLowerCase()).matcher(e.getMessage().toLowerCase()).find()) {
				throw new FormattedException("message % does not match %", e, e.getMessage(), regex);
			}
		});
	}
}
