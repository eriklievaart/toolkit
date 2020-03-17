package com.eriklievaart.toolkit.io.api.ini;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaException;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class IniNodeValidatorU {
	private LogTemplate log = new LogTemplate(getClass());

	@Test
	public void runTests() throws IOException {
		String resource = "/com/eriklievaart/toolkit/io/ini/IniNodeValidatorTests.ini";
		List<IniNode> root = IniNodeIO.read(getClass().getResourceAsStream(resource));

		List<String> failed = NewCollection.list();

		for (IniNode testNode : root) {
			String testName = testNode.getProperty("name");
			boolean expected = Boolean.valueOf(testNode.getProperty("pass"));
			IniNode schemaNode = testNode.getChild("schema");
			IniNode contentNode = testNode.getChild("content");
			IniNodeValidationResult result = validate(contentNode, schemaNode);
			if (result.isValid() != expected) {
				failed.add(testName);
				log.warn("Test failed [$ of $]: $ => $", failed.size(), root.size(), testName, result.getErrorMessage());
				validate(contentNode, schemaNode);
			}
		}
		CheckCollection.isEmpty(failed);
	}

	private static IniNodeValidationResult validate(IniNode contentNode, IniNode schemaNode) {
		IniNodeValidationResult result = new IniNodeValidationResult();
		try {
			IniNodeValidator.validate(contentNode, schemaNode);

		} catch (IniSchemaException e) {
			result.setError("validation failed: " + e.getMessage());
		}
		return result;
	}
}