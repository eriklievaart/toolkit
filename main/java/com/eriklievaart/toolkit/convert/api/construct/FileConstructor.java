package com.eriklievaart.toolkit.convert.api.construct;

import java.io.File;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.serialize.FileSerializer;
import com.eriklievaart.toolkit.convert.api.validate.AlwaysValidate;

public class FileConstructor extends AbstractConstructor<File> {

	@Override
	public File constructObject(String str) throws ConversionException {
		return new File(str);
	}

	@Override
	public Converter<File> createConverter() {
		return new Converter<>(this, new AlwaysValidate(), new FileSerializer());
	}
}