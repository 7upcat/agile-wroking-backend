package org.catframework.agileworking.web.support;

import org.catframework.agileworking.BusinessException;
import org.catframework.agileworking.common.ResponseCodes;

public class DefaultResult<T> implements Result<T> {

	/**
	 * 创建一个成功的结果，不含 payload
	 */
	public static Result<?> newResult() {
		DefaultResult<?> result = new DefaultResult<>();
		result.status = Result.STATUS_SUCCESS;
		return result;
	}

	/**
	 * 创建一个成功的结果
	 * 
	 * @param payload 结果中的数据
	 * @return 新创建的交易结果
	 */
	public static <T> Result<T> newResult(T payload) {
		DefaultResult<T> result = new DefaultResult<>();
		result.status = Result.STATUS_SUCCESS;
		result.responseCode = ResponseCodes.RESPONSE_CODE_SUCCESS;
		result.payload = payload;
		return result;
	}

	/**
	 * 创建一个失败的结果
	 * 
	 * @param ex 导致交易失败的具体异常
	 * @return 新创建的交易结果
	 */
	public static <T> Result<T> newFailResult(Throwable ex) {
		DefaultResult<T> result = new DefaultResult<>();
		result.status = Result.STATUS_FAIL;
		result.responseMessage = ex.getMessage();
		result.responseCode = (ex instanceof BusinessException) ? ((BusinessException) ex).getCode()
				: ResponseCodes.RESPONSE_CODE_SYSTEM_ERROR;
		return result;
	}

	private DefaultResult() {
	}

	private int status;

	private T payload;

	private String responseCode;

	private String responseMessage;

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public boolean isSuccess() {
		return Result.STATUS_SUCCESS == status;
	}

	@Override
	public String getResponseCode() {
		return responseCode;
	}

	@Override
	public String getResponseMessage() {
		return responseMessage;
	}

	@Override
	public T getPayload() {
		return payload;
	}
}
