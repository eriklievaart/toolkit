package com.eriklievaart.toolkit.lang.api.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatWrapper {

	private SimpleDateFormat format;

	public SimpleDateFormatWrapper(SimpleDateFormat format) {
		this.format = format;
	}

	public Date toDate(String dateString) throws ParseException {
		return format.parse(dateString);
	}

	public long toTimestamp(String dateString) throws ParseException {
		return toDate(dateString).getTime();
	}

	public String toString(Date date) {
		return format.format(date);
	}

	public String toString(long timestamp) {
		return toString(new Date(timestamp));
	}
}
