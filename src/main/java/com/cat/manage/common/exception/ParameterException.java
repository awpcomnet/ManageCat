package com.cat.manage.common.exception;

public class ParameterException extends RuntimeException {

	private String exceptionCode;
	private String exceptionMessage;

	public ParameterException(String exceptionCode, String exceptionMessage) {
		super("参数校验错误 " + exceptionMessage);

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

	private static final long serialVersionUID = 4424495805166382805L;
}
