package com.eriklievaart.toolkit.runtime.api;

import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class CliStreams {
	LogTemplate log = new LogTemplate(getClass());

	private CliOutput out;
	private CliOutput err;

	public CliStreams() {
		out = new CliOutput() {
			@Override
			public void println(String line) {
				log.trace(line);
			}
		};
		err = new CliOutput() {
			@Override
			public void println(String line) {
				log.warn(line);
			}
		};
	}

	public CliStreams(CliOutput out, CliOutput err) {
		this.out = out;
		this.err = err;
	}

	public CliOutput getNormalOutput() {
		return out;
	}

	public CliOutput getErrorOutput() {
		return err;
	}

	public void setNormalOutput(CliOutput output) {
		out = output;
	}

	public void setErrorOutput(CliOutput output) {
		err = output;
	}

	public void debug(String message) {
		log.debug(message);
	}

	public void thrown(Exception e) {
		err.println(e.getMessage());
	}

}
