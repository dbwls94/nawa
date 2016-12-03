package org.nawa.exception;

public class InsufficientAuthorityException extends Exception{

	public InsufficientAuthorityException() {
		super();
	}

	public InsufficientAuthorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InsufficientAuthorityException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientAuthorityException(String message) {
		super(message);
	}

	public InsufficientAuthorityException(Throwable cause) {
		super(cause);
	}

} //class