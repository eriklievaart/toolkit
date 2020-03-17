package com.eriklievaart.toolkit.convert.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFileType;

public class ConvertersU {

	@Test
	public void convertToString() throws ConversionException {
		String value = Converters.BASIC_CONVERTERS.convertToString(123);
		Check.isEqual(value, "123");
	}

	@Test
	public void convertToInteger() throws ConversionException {
		int value = Converters.BASIC_CONVERTERS.to(Integer.class, "123");
		Check.isEqual(value, 123);
	}

	@Test
	public void convertToStringEnum() throws ConversionException {
		String value = Converters.BASIC_CONVERTERS.convertToString(VirtualFileType.DIRECTORY);
		Check.isEqual(value, "DIRECTORY");
	}

	@Test
	public void convertToEnum() throws ConversionException {
		VirtualFileType value = Converters.BASIC_CONVERTERS.to(VirtualFileType.class, "DIRECTORY");
		Check.isEqual(value, VirtualFileType.DIRECTORY);
	}
}