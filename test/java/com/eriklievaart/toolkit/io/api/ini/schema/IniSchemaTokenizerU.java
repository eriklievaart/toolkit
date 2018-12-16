package com.eriklievaart.toolkit.io.api.ini.schema;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.CollectionTool;

public class IniSchemaTokenizerU {

	@Test
	public void singleRequired() {
		IniSchemaToken token = CollectionTool.getSingle(tokenize("required"));
		Check.isEqual(token.getRaw(), "required");
		Check.isEqual(token.getMultiplicity(), IniSchemaMulitplicity.REQUIRED);
	}

	@Test
	public void singleOptional() {
		IniSchemaToken token = CollectionTool.getSingle(tokenize("optional?"));
		Check.isEqual(token.getRaw(), "optional");
		Check.isEqual(token.getMultiplicity(), IniSchemaMulitplicity.OPTIONAL);
	}

	@Test
	public void singleAny() {
		IniSchemaToken token = CollectionTool.getSingle(tokenize("any*"));
		Check.isEqual(token.getRaw(), "any");
		Check.isEqual(token.getMultiplicity(), IniSchemaMulitplicity.ANY);
	}

	@Test
	public void doubleRequired() {
		List<IniSchemaToken> tokens = tokenize("required1 required2");
		Iterator<IniSchemaToken> iterator = tokens.iterator();

		IniSchemaToken token1 = iterator.next();
		Check.isEqual(token1.getRaw(), "required1");
		Check.isEqual(token1.getMultiplicity(), IniSchemaMulitplicity.REQUIRED);

		IniSchemaToken token2 = iterator.next();
		Check.isEqual(token2.getRaw(), "required2");
		Check.isEqual(token2.getMultiplicity(), IniSchemaMulitplicity.REQUIRED);
	}

	@Test
	public void doubleRequiredComma() {
		List<IniSchemaToken> tokens = tokenize("required1, required2");
		Iterator<IniSchemaToken> iterator = tokens.iterator();

		IniSchemaToken token1 = iterator.next();
		Check.isEqual(token1.getRaw(), "required1");
		Check.isEqual(token1.getMultiplicity(), IniSchemaMulitplicity.REQUIRED);

		IniSchemaToken token2 = iterator.next();
		Check.isEqual(token2.getRaw(), "required2");
		Check.isEqual(token2.getMultiplicity(), IniSchemaMulitplicity.REQUIRED);
	}

	@Test
	public void doubleAny() {
		List<IniSchemaToken> tokens = tokenize("any1* any2*");
		Iterator<IniSchemaToken> iterator = tokens.iterator();

		IniSchemaToken token1 = iterator.next();
		Check.isEqual(token1.getRaw(), "any1");
		Check.isEqual(token1.getMultiplicity(), IniSchemaMulitplicity.ANY);

		IniSchemaToken token2 = iterator.next();
		Check.isEqual(token2.getRaw(), "any2");
		Check.isEqual(token2.getMultiplicity(), IniSchemaMulitplicity.ANY);
	}

	private List<IniSchemaToken> tokenize(String input) {
		List<IniSchemaToken> tokens = new IniSchemaTokenizer(input).getTokens();
		return tokens;
	}
}
