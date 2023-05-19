package com.eriklievaart.toolkit.lang.api.date;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class TimestampTool {

	public static final long ONE_SECOND = 1000;
	public static final long ONE_MINUTE = 60 * ONE_SECOND;
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	public static final long ONE_DAY = 24 * ONE_HOUR;
	public static final long ONE_WEEK = 7 * ONE_DAY;

	public static final long MONTH_28 = 28 * ONE_DAY;
	public static final long MONTH_29 = 29 * ONE_DAY;
	public static final long MONTH_30 = 30 * ONE_DAY;
	public static final long MONTH_31 = 31 * ONE_DAY;

	public static final long YEAR_355 = 355 * ONE_DAY;
	public static final long YEAR_356 = 356 * ONE_DAY;

	public static String diffHumanReadable(long a, long b) {
		return humanReadable(Math.abs(a - b));
	}

	public static String humanReadable(long millis) {
		if (millis > 2 * YEAR_355) {
			return millis / YEAR_355 + "y";
		}
		if (millis > 2 * ONE_DAY) {
			return millis / ONE_DAY + "d";
		}
		if (millis > 2 * ONE_HOUR) {
			return millis / ONE_HOUR + "h";
		}
		if (millis > 2 * ONE_MINUTE) {
			return millis / ONE_MINUTE + "m";
		}
		if (millis > 2 * ONE_SECOND) {
			return millis / ONE_SECOND + "s";
		}
		return millis + "ms";
	}

	/**
	 * Converts timestamps to UTC for system timezone
	 */
	public static long timestampToUtc(long timestamp) {
		return timestampToUtc(timestamp, ZoneOffset.systemDefault());
	}

	/**
	 * Converts timestamps to for specified ZoneId
	 */
	public static long timestampToUtc(long timestamp, ZoneId zoneId) {
		return timestampToUtc(timestamp, TimeZone.getTimeZone(zoneId));
	}

	/**
	 * Converts timestamps to UTC for specified TimeZone
	 */
	private static long timestampToUtc(long timestamp, TimeZone zone) {
		return timestamp + zone.getOffset(timestamp);
	}
}