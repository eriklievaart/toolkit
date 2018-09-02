package com.eriklievaart.toolkit.lang.api.date;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatFactory;
import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatWrapper;

public class SimpleDateFormatFactoryU {

	@Test
	public void getWrapperNL() throws ParseException {
		String dateString = "21-03-1982";
		SimpleDateFormatWrapper wrapper = SimpleDateFormatFactory.getWrapperNL();
		Date date = wrapper.toDate(dateString);
		CheckStr.isEqual(wrapper.toString(date), dateString);
	}
}
