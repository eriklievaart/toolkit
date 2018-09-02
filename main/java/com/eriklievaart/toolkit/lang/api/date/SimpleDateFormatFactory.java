package com.eriklievaart.toolkit.lang.api.date;

import java.text.SimpleDateFormat;

public class SimpleDateFormatFactory {

	private static final String NL_DATE_FORMAT = "dd-MM-yyyy";

	public static SimpleDateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}

	public static SimpleDateFormatWrapper getWrapper(SimpleDateFormat format) {
		return new SimpleDateFormatWrapper(format);
	}

	public static SimpleDateFormatWrapper getWrapper(String format) {
		return new SimpleDateFormatWrapper(getFormat(format));
	}

	public static SimpleDateFormat getFormatNL() {
		return getFormat(NL_DATE_FORMAT);
	}

	public static SimpleDateFormatWrapper getWrapperNL() {
		return getWrapper(getFormatNL());
	}

}
