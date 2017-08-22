package org.catframework.agileworking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * {@link Date} 解析、格式化的脚手架构工具类.
 * 
 * @author devzzm
 *
 */
public final class DateUtils {

	/** 简单的日期格式： yyyy-MM-dd */
	public static final String PATTERN_SIMPLE_DATE = "yyyy-MM-dd";

	public static final Date parse(String source, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static final boolean isSameWeekday(Date d1, Date d2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		return cal1.get(Calendar.DAY_OF_WEEK) == cal2.get(Calendar.DAY_OF_WEEK);
	}
}
