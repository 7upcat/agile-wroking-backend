package org.catframework.agileworking.common;

/**
 * 集中的定义系统中的响应码，格式如下：
 * <ul>
 * <li>成功：统一使用 {@link #RESPONSE_CODE_SUCCESS}
 * <li>失败：ER+4位数字
 * </ul>
 * 
 * @author devzzm
 *
 */
public final class ResponseCodes {

	/** 响应码：成功. */
	public static final String RESPONSE_CODE_SUCCESS = "SC0000";

	/** 响应码：默认的系统处理异常，用于非预期的运行时异常. */
	public static final String RESPONSE_CODE_SYSTEM_ERROR = "ER0001";
}
