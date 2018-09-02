package com.eriklievaart.toolkit.runtime.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class CliCommandU {

	@Test
	public void splitCommand() {
		Check.isEqual(CliCommand.splitCommandLine("ls -l"), new String[] { "ls", "-l" });
	}

	@Test
	public void splitCommandPadding() {
		Check.isEqual(CliCommand.splitCommandLine(" ls -l "), new String[] { "ls", "-l" });
	}

	@Test
	public void splitCommandSingle() {
		Check.isEqual(CliCommand.splitCommandLine("ls"), new String[] { "ls" });
	}

	@Test(expected = AssertionException.class)
	public void splitArgumentsNull() {
		CliCommand.splitCommandLine(null);
	}

	@Test(expected = AssertionException.class)
	public void splitArgumentsEmpty() {
		CliCommand.splitCommandLine("");
	}

	@Test(expected = AssertionException.class)
	public void splitArgumentsBlank() {
		CliCommand.splitCommandLine("  ");
	}

	@Test
	public void splitCommandAndArgumentsNoArgument() {
		Check.isEqual(CliCommand.splitCommandLine("ls"), new String[] { "ls" });
	}

	@Test
	public void splitCommandAndArgumentsSingleArgument() {
		Check.isEqual(CliCommand.splitCommandLine("ls -l"), new String[] { "ls", "-l" });
	}

	@Test
	public void splitCommandAndArgumentsDoubleArgument() {
		Check.isEqual(CliCommand.splitCommandLine("ls -a -l"), new String[] { "ls", "-a", "-l" });
	}

	@Test
	public void splitCommandAndArgumentsWhitespace() {
		Check.isEqual(CliCommand.splitCommandLine("cd go\\whome"), new String[] { "cd", "go home" });
	}

	@Test
	public void splitCommandAndArgumentsBackspace() {
		Check.isEqual(CliCommand.splitCommandLine("echo \\b"), new String[] { "echo", "\\" });
	}

}
