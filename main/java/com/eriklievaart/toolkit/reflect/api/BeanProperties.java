package com.eriklievaart.toolkit.reflect.api;

import java.util.Arrays;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.reflect.api.method.PropertyWrapper;

public class BeanProperties {

	public static Object resolveProperty(String property, Map<String, ?> primary, Map<String, ?>... secondary) {
		String[] path = PatternTool.split("\\.", property.trim());

		Object bean = resolveBean(path[0], primary, secondary);
		Check.notNull(bean, "% is <null> for EL:%", path[0], property);

		for (int i = 1; i < path.length; i++) {
			Map<String, PropertyWrapper> props = PropertyTool.getPropertyMap(bean.getClass());
			Check.isTrue(props.containsKey(path[i]), "% not found on % for %", path[i], bean.getClass(), property);
			bean = props.get(path[i]).getAccessor(bean).invoke();
		}
		return bean;
	}

	private static Object resolveBean(String bean, Map<String, ?> primary, Map<String, ?>... secondary) {
		if (primary.containsKey(bean)) {
			return primary.get(bean);
		}
		if (secondary.length > 1) {
			return resolveBean(bean, secondary[0], Arrays.copyOfRange(secondary, 1, secondary.length - 1));
		}
		if (secondary.length == 1) {
			return resolveBean(bean, secondary[0]);
		}
		return null;
	}
}
