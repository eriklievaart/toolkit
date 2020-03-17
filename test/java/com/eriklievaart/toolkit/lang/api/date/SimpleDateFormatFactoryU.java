package com.eriklievaart.toolkit.lang.api.date;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class SimpleDateFormatFactoryU {

	@Test
	public void getWrapperDateNL() throws ParseException {
		String dateString = "20-04-1980";
		SimpleDateFormatWrapper wrapper = SimpleDateFormatFactory.getDateWrapperNL();
		Date date = wrapper.toDate(dateString);
		CheckStr.isEqual(wrapper.toString(date), dateString);
	}

	@Test
	public void getWrapperNL() throws ParseException {
		String dateString = "19-05-1979 12:05:30";
		SimpleDateFormatWrapper wrapper = SimpleDateFormatFactory.getDateTimeWrapperNL();
		Date date = wrapper.toDate(dateString);
		CheckStr.isEqual(wrapper.toString(date), dateString);
	}
}