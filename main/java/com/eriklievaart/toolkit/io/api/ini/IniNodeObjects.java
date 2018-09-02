package com.eriklievaart.toolkit.io.api.ini;

import java.util.Map;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.PropertyTool;

public class IniNodeObjects {

	public static <E> E newInstance(IniNode node) {
		Check.notNull(node);
		String type = node.getProperty("type");
		Map<String, String> properties = NewCollection.mapNotNull();
		if (node.hasChild("properties")) {
			IniNode propertiesNode = node.getChild("properties");
			properties = propertiesNode.getPropertiesMap();
		}
		E instance = LiteralTool.newInstance(type);
		PropertyTool.convertAndInjectProperties(instance, properties);
		return instance;
	}
}
