package org.catframework.agileworking.utils;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testFormat() {
		Date date = DateUtils.parse("2017-08-31", DateUtils.PATTERN_SIMPLE_DATE);
		Assert.assertEquals("2017年08月31日",DateUtils.format(date, "yyyy年MM月dd日"));
	}

}
