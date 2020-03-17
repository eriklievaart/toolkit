package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.util.Optional;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class ResourceToolU {

	@Test
	public void getOptionalFile() {
		Optional<File> file = ResourceTool.getOptionalFile("/ResourceTool.txt");
		CheckFile.isFile(file.get());
	}

	@Test
	public void getOptionalFileMissing() {
		Optional<File> file = ResourceTool.getOptionalFile("/idonotexist.txt");
		Check.isFalse(file.isPresent());
	}
}