package com.eriklievaart.toolkit.lang.api.date;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class TimestampToolU {

	@Test
	public void diffHumanReadableYear() {
		Check.isEqual(TimestampTool.diffHumanReadable(0, 3 * TimestampTool.YEAR_355), "3y");
	}

	@Test
	public void diffHumanReadableDay() {
		Check.isEqual(TimestampTool.diffHumanReadable(0, 50 * TimestampTool.ONE_HOUR), "2d");
	}
}
