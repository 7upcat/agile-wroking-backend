package org.catframework.agileworking.service.impl;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class ScheduleServiceImplTest {

	@Test
	public void testCalendar() throws ParseException {
		Calendar cal= Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(dateFormat.parse("2017-08-01"));
		assertEquals(Calendar.TUESDAY, cal.get(Calendar.DAY_OF_WEEK));
	}
}
