package com.app.willywonkafactory.rest.exception;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6434704455929867758L;

	private int code;
	private int status;
	private String message;
	private String link;
	private String developerMessage;

	public AppRuntimeException() {
		this(SC_INTERNAL_SERVER_ERROR);
	}

	public AppRuntimeException(int status) {
		this(status, -1, "Unspecified application error");
	}

	public AppRuntimeException(int status, int code, String message) {
		this(status, code, message, EMPTY, EMPTY);
	}

	public AppRuntimeException(int status, int code, String message, String link, String developerMessage) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.link = link;
		this.developerMessage = developerMessage;
	}

	public int getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String getLink() {
		return link;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}
}
