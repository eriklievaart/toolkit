package com.eriklievaart.toolkit.script.api;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class ScriptExecutor {

	private ScriptEngine engine;

	/**
	 * Create a ScriptExecutor.
	 *
	 * @param extension
	 *            extension of script files, example "js".
	 */
	public ScriptExecutor(String extension) {
		engine = new ScriptEngineManager().getEngineByExtension(extension);
	}

	public void setAttribute(String name, Object o) {
		engine.getContext().setAttribute(name, o, ScriptContext.ENGINE_SCOPE);
	}

	public String runCode(String code) {
		assert engine != null;
		try {
			Object eval = engine.eval(code);
			return eval == null ? "" : eval.toString();

		} catch (ScriptException e) {
			throw new FormattedException("Unable to run script.\n$\n$", e, e.getMessage(), Str.addLineNumbers(code));
		}
	}
}