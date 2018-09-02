package com.eriklievaart.toolkit.script.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.script.api.ScriptExecutor;

public class ScriptExecutorU {

	@Test
	public void test() {
		ScriptExecutor executor = new ScriptExecutor("js");
		String result = executor.runCode("out = 'baka'");
		Check.isEqual(result, "baka");
	}
}
