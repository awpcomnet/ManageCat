package com.cat.manage.common.exception;

public class BusinessException extends RuntimeException {

	private String exceptionCode;
	private String exceptionMessage;

	public BusinessException(String exceptionCode, String exceptionMessage) {
		super("业务异常 " + exceptionMessage);
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	private static final long serialVersionUID = 2544545855821461930L;
}
