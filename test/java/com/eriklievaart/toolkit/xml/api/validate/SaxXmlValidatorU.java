package com.eriklievaart.toolkit.xml.api.validate;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.collection.CollectionTool;

public class SaxXmlValidatorU {

	@Test
	public void validateXmlPass() {
		InputStream is = StreamTool.toInputStream("<root></root>");
		List<SaxParseExceptionWrapper> exceptions = SaxXmlValidator.validateXML(is);
		CheckCollection.isEmpty(exceptions);
	}

	@Test
	public void validateXmlFail() {
		InputStream is = StreamTool.toInputStream("<root>");
		List<SaxParseExceptionWrapper> exceptions = SaxXmlValidator.validateXML(is);
		String message = CollectionTool.getSingle(exceptions).getMessage();
		CheckStr.containsIgnoreCase(message, "must start and end");
	}
}
