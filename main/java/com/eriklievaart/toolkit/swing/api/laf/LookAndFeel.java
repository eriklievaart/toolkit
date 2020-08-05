package com.eriklievaart.toolkit.swing.api.laf;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;

/**
 * Utility class for loading a look and feel from a file. By default /laf.txt is loaded from the classpath.
 *
 * LAF properties are specified in a property "like" syntax. For example to change the list background:
 *
 * <pre>
 * List.background=color 0,0,0
 * </pre>
 *
 * The first word in the property value specifies the {@link Converter} to use for turning the value into an Object.
 * Additional {@link Converter}'s can be registered using the {@link #useConverter(String, Converter)} method. Values
 * can be specified inline, as shown above or registered as variables which can be used by multiple properties. Here is
 * an example of variable usage:
 *
 * <pre>
 * $dark=color 34,40,49
 * Panel.background=$dark
 * List.background=$dark
 * </pre>
 */
public class LookAndFeel {

	private Map<String, Converter<?>> converters = new Hashtable<>();

	public static void listKeys() {
		UIDefaults defaults = UIManager.getDefaults();
		Enumeration<?> e = defaults.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			Object current = defaults.get(key);
			String value = current == null ? "" : current.getClass().toString();
			System.out.println(key + "=" + value);
		}
	}

	public static LookAndFeel instance() {
		LookAndFeel laf = new LookAndFeel();

		laf.useConverter("color", new ColorConstructor().createConverter());
		laf.useConverter("font", new FontConstructor().createConverter());

		return laf;
	}

	public void useConverter(String key, Converter<?> converter) {
		CheckCollection.notPresent(converters, key);
		converters.put(key, converter);
	}

	public void load() {
		load(ResourceTool.getInputStream("/laf.txt"));
	}

	public void load(InputStream is) {
		parseConfig(is).forEach((key, value) -> UIManager.getLookAndFeelDefaults().put(key, value));
	}

	public Map<String, Object> parseConfig(InputStream is) {
		LineFilter filter = new LineFilter(StreamTool.toString(is));
		List<String> lines = filter.eof().dropHash().dropBlank().trim().list();
		Map<String, Object> variables = loadVariables(ListTool.filter(lines, s -> s.startsWith("$")));
		return createSettingsMap(ListTool.filter(lines, s -> !s.startsWith("$")), variables);
	}

	Map<String, Object> createSettingsMap(List<String> lines, Map<String, Object> variables) {
		Map<String, Object> settings = new Hashtable<>();

		for (String line : lines) {
			String[] keyToValue = line.split("=", 2);
			Check.isTrue(keyToValue.length == 2, "invalid line: %", line);
			String key = keyToValue[0].trim();
			String value = keyToValue[1].trim();

			if (value.startsWith("$")) {
				CheckCollection.isPresent(variables, value.substring(1));
				settings.put(key, variables.get(value.substring(1)));
			} else {
				settings.put(key, convert(value));
			}
		}
		return settings;
	}

	Map<String, Object> loadVariables(List<String> lines) {
		Map<String, Object> variables = new Hashtable<>();

		for (String line : lines) {
			String[] variableToValue = line.substring(1).trim().split("\\s*+=\\s*+", 2);
			Check.isTrue(variableToValue.length == 2, "expecting '=' on line %", line);
			variables.put(variableToValue[0], convert(variableToValue[1]));
		}
		return variables;
	}

	@SuppressWarnings("unchecked")
	<E> E convert(String raw) {
		String[] typeToValue = raw.trim().split("\\s++", 2);
		Check.isTrue(typeToValue.length == 2, "converter and value required: %", raw);
		String converter = typeToValue[0];
		String value = typeToValue[1];
		return (E) converters.get(converter).convertToObject(value);
	}
}
