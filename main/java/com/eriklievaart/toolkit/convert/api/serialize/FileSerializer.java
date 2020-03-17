package com.eriklievaart.toolkit.convert.api.serialize;

import java.io.File;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Serializer;

/**
 * Serialized Files to a String containing their absolute path.
 */
public class FileSerializer implements Serializer<File> {

	@Override
	public String toString(File instance) throws ConversionException {
		return instance.getAbsolutePath();
	}
}