package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class PropertiesIO {
	private static LogTemplate log = new LogTemplate(PropertiesIO.class);

	public static void storeStrings(Map<String, String> props, File file) {
		store(convertToStrings(props), file);
	}

	public static void storeStrings(Map<String, String> props, OutputStream os) {
		store(convertToStrings(props), os);
	}

	private static Properties convertToStrings(Map<String, String> props) {
		Properties properties = new Properties();
		for (Entry<String, String> entry : props.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
		return properties;
	}

	public static void store(Properties props, File file) {
		log.debug("Storing property file $", file);
		file.getParentFile().mkdirs();
		try {
			store(props, new FileOutputStream(file));
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void store(Properties props, OutputStream os) {
		try {
			props.store(os, null);

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			StreamTool.close(os);
		}
	}

	public static Map<String, String> loadStrings(File file) {
		return toMap(load(file));
	}

	public static Map<String, String> loadStrings(InputStream is) {
		return toMap(load(is));
	}

	private static Map<String, String> toMap(Properties properties) {
		Map<String, String> map = NewCollection.mapNotNull();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			map.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return map;
	}

	public static Properties load(File file) {
		try {
			return load(new FileInputStream(file));
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static Properties load(InputStream is) {
		Properties props = new Properties();

		try {
			props.load(is);

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			StreamTool.close(is);
		}
		return props;
	}
}