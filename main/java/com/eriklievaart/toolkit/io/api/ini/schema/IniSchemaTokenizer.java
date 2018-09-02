package com.eriklievaart.toolkit.io.api.ini.schema;

import java.util.List;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.CharIterator;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class IniSchemaTokenizer {

	private StringBuilder builder = new StringBuilder();
	private List<IniSchemaToken> tokens = NewCollection.list();

	public IniSchemaTokenizer(String input) {
		if (input != null) {
			tokenize(input);
		}
	}

	private void tokenize(String input) {
		CharIterator iterator = new CharIterator(input);
		while (iterator.hasNext()) {
			char c = iterator.next();

			if (c == '?') {
				addToken(IniSchemaMulitplicity.OPTIONAL);

			} else if (c == '*') {
				addToken(IniSchemaMulitplicity.ANY);

			} else if (c == ' ' || c == ',') {
				if (!Str.isBlank(builder.toString())) {
					addToken(IniSchemaMulitplicity.REQUIRED);
				}

			} else {
				builder.append(c);
			}
		}
		if (!Str.isBlank(builder.toString())) {
			addToken(IniSchemaMulitplicity.REQUIRED);
		}
	}

	private void addToken(IniSchemaMulitplicity multiplicity) {
		tokens.add(new IniSchemaToken(builder.toString(), multiplicity));
		builder = new StringBuilder();
	}

	public List<IniSchemaToken> getTokens() {
		return tokens;
	}
}
