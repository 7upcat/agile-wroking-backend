package org.catframework.agileworking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
