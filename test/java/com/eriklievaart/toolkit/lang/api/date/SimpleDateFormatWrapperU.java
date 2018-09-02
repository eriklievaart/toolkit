package com.eriklievaart.toolkit.lang.api.date;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatFactory;
import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatWrapper;
import com.eriklievaart.toolkit.lang.api.date.TimestampTool;

public class SimpleDateFormatWrapperU {

	private SimpleDateFormatWrapper wrapper = new SimpleDateFormatWrapper(SimpleDateFormatFactory.getFormatNL());

	@Test
	public void toDate() throws ParseException {
		Date date = wrapper.toDate("21-03-1982");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		Check.isEqual(21, calendar.get(GregorianCalendar.DAY_OF_MONTH));
		Check.isEqual(Calendar.MARCH, calendar.get(GregorianCalendar.MONTH));
		Check.isEqual(1982, calendar.get(GregorianCalendar.YEAR));
	}

	@Test(expected = ParseException.class)
	public void toDateInvalidInput() throws ParseException {
		wrapper.toDate("21031982");
	}

	@Test
	public void toTimestamp() throws ParseException {
		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setTimeInMillis(wrapper.toTimestamp("21-03-1982"));

		Check.isEqual(21, calendar.get(GregorianCalendar.DAY_OF_MONTH));
		Check.isEqual(Calendar.MARCH, calendar.get(GregorianCalendar.MONTH));
		Check.isEqual(1982, calendar.get(GregorianCalendar.YEAR));
	}

	@Test
	public void toStringTimestamp() {
		Check.isEqual(wrapper.toString(0), "01-01-1970");
		Check.isEqual(wrapper.toString(TimestampTool.ONE_DAY), "02-01-1970");
		Check.isEqual(wrapper.toString(TimestampTool.ONE_WEEK), "08-01-1970");
	}

	@Test
	public void toStringDate() {
		Date date = new GregorianCalendar(1982, Calendar.MARCH, 21).getTime();
		Check.isEqual(wrapper.toString(date), "21-03-1982");
	}

}
