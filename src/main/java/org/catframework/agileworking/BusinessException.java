package org.catframework.agileworking;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3999713779564898790L;

	private String code;

	public BusinessException(String code) {
		this.code = code;
	}

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
