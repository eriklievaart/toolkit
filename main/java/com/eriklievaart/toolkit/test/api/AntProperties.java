package com.eriklievaart.toolkit.test.api;

public class AntProperties {

	public static AntProperty getSpoolDir() {
		return new AntProperty("build.spool.dir");
	}

	public static AntProperty getSqlDir() {
		return new AntProperty("source.main.sql.dir");
	}

	public static AntProperty getRootSrcDir() {
		return new AntProperty("source.project.src.dir");
	}

	public static AntProperty getJavaSourceDir() {
		return new AntProperty("source.main.java.dir");
	}

	public static AntProperty getResourceDir() {
		return new AntProperty("source.main.resource.dir");
	}

	public static AntProperty getZipDir() {
		return new AntProperty("source.main.zip.dir");
	}

	public static AntProperty getTestJavaSourceDir() {
		return new AntProperty("source.test.java.dir");
	}

	public static AntProperty getRunDir() {
		return new AntProperty("build.run.dir");
	}

	public static AntProperty getJavaClassesDir() {
		return new AntProperty("build.main.classes.dir");
	}

	public static AntProperty getTestJavaClassesDir() {
		return new AntProperty("build.test.classes.dir");
	}

	public static AntProperty getGenerateDir() {
		return new AntProperty("build.generated.dir");
	}

	public static boolean isTestMode() {
		String property = System.getProperty("application.test.mode");
		return property == null ? false : property.equalsIgnoreCase("true");
	}
}
