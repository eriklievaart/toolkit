package com.eriklievaart.toolkit.io.api.ini;

public class IniNodeValidationResult {

	private String message = "validation passed";
	private boolean valid = true;

	public void setError(String message) {
		valid = false;
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public String getErrorMessage() {
		return message;
	}
}