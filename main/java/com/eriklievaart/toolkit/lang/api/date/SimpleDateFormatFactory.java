package com.eriklievaart.toolkit.lang.api.date;

import java.text.SimpleDateFormat;

public class SimpleDateFormatFactory {

	private static final String NL_DATE_FORMAT = "dd-MM-yyyy";
	private static final String NL_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	public static SimpleDateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}

	public static SimpleDateFormatWrapper getWrapper(SimpleDateFormat format) {
		return new SimpleDateFormatWrapper(format);
	}

	public static SimpleDateFormatWrapper getWrapper(String format) {
		return new SimpleDateFormatWrapper(getFormat(format));
	}

	public static SimpleDateFormat getDateFormatNL() {
		return getFormat(NL_DATE_FORMAT);
	}

	public static SimpleDateFormatWrapper getDateWrapperNL() {
		return getWrapper(getDateFormatNL());
	}

	public static SimpleDateFormat getDateTimeFormatNL() {
		return getFormat(NL_DATE_TIME_FORMAT);
	}

	public static SimpleDateFormatWrapper getDateTimeWrapperNL() {
		return getWrapper(getDateTimeFormatNL());
	}
}