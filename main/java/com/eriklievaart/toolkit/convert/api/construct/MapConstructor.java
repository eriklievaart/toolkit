package com.eriklievaart.toolkit.convert.api.construct;

import java.util.Map;

import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.validate.MapValidator;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;

/**
 * Constructs Map's from String's. Use '|' to separate entries and '=' to separate key - value pairs.
 *
 * @author Erik Lievaart
 */
@SuppressWarnings("unchecked")
public class MapConstructor extends AbstractConstructor<Map> {

	@Override
	public Map<?, ?> constructObject(final String str) {
		Map map = NewCollection.map();
		for (String entry : PatternTool.split("#", str)) {
			String[] split = PatternTool.split("=", entry);
			map.put(split[0], split[1]);
		}
		return map;
	}

	@Override
	public Converter<Map> createConverter() {
		return createConverter(new MapValidator());
	}
}
