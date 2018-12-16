package com.eriklievaart.toolkit.reflect.api;

import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.reflect.api.PropertyTool;
import com.eriklievaart.toolkit.reflect.api.method.PropertyWrapper;

public class PropertyToolU {

	@Test
	public void getDescriptors() {
		Map<String, PropertyWrapper> properties = PropertyTool.getPropertyMap("java.awt.Point");
		Check.isTrue(properties.containsKey("x"));
		Check.isTrue(properties.get("x").isReadable());
		Check.isFalse(properties.get("x").isWritable());

		Check.isTrue(properties.containsKey("location"));
		Check.isTrue(properties.get("location").isReadable());
		Check.isTrue(properties.get("location").isWritable());

		Check.isFalse(properties.containsKey("What's orange and sounds like a parrot? A carrot"));
	}

	@Test
	public void convertAndInjectProperties() {
		Thread thread = new Thread();
		PropertyTool.convertAndInjectProperties(thread, MapTool.of("name", "Goblin Kite", "priority", "8"));
		Check.isEqual(thread.getName(), "Goblin Kite");
		Check.isEqual(thread.getPriority(), 8);
	}
}
