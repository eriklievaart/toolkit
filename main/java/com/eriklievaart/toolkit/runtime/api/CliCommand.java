package com.eriklievaart.toolkit.runtime.api;

import java.util.ArrayList;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.lang.api.str.StringEscape;

/**
 * A command that can be invoked on the native shell.
 * 
 * @author Erik Lievaart
 */
@Immutable
public class CliCommand {

	/** Replace escape characters: '\b' & '\w' => '\' & ' '. */
	private static final StringEscape COMMAND_LINE = new StringEscape(MapTool.of('b', '\\', 'w', ' '));

	private final List<String> cmdarray = new ArrayList<String>();

	private CliCommand(final String... cmd) {
		ListTool.addAll(cmdarray, cmd);
	}

	/**
	 * Create a new command with the supplied arguments.
	 */
	public CliCommand(final String command, final String... params) {
		cmdarray.add(command);
		ListTool.addAll(cmdarray, params);
	}

	/**
	 * Create a command from a String. The line will be split on whitespace and the first word will be the command
	 * invoked. All following words will be arguments to the command. Whitespace and backslashes in arguments must be
	 * escaped using \w and \b respectively.
	 */
	public static CliCommand from(final String line) {
		return new CliCommand(splitCommandLine(line));
	}

	/**
	 * Escape whitespace characters and backslashes in an argument.
	 */
	public static String escape(final String input) {
		return COMMAND_LINE.escape(input);
	}

	static String[] splitCommandLine(final String line) {
		Check.notBlank(line);
		return COMMAND_LINE.unescape(PatternTool.split("\\s", line.trim()));
	}

	/**
	 * Returns the command and its arguments in order.
	 */
	public String[] cmd() {
		return cmdarray.size() == 0 ? null : cmdarray.toArray(new String[0]);
	}

	@Override
	public String toString() {
		return cmdarray.toString();
	}
}
