package org.catframework.agileworking.web.support;

/**
 * 统一封装请求结果的接口，便于集中的进行异常处理以及便于调用方使用.
 * 
 * @author devzzm
 */
public interface Result<T> {

	/** 交易结果状态：成功. */
	public static final int STATUS_SUCCESS = 0;

	/** 交易结果状态：失败. */
	public static final int STATUS_FAIL = 1;

	/**
	 * @return 此结果的状态
	 * @see Result#STATUS_FAIL
	 * @see Result#STATUS_SUCCESS
	 */
	int getStatus();

	/**
	 * @return 结果状态为 {@link Result#STATUS_SUCCESS} 返回 <code>true</code>
	 */
	boolean isSuccess();

	/**
	 * @return 响应码
	 */
	String getResponseCode();

	/**
	 * @return 响应消息
	 */
	String getResponseMessage();

	/**
	 * @return 消息所承载的数据
	 */
	T getPayload();
}
