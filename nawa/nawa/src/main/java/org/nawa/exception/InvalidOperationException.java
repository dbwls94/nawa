package org.nawa.exception;

public class InvalidOperationException extends Exception{

	public InvalidOperationException() {
		super();
	}

	public InvalidOperationException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public InvalidOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidOperationException(String arg0) {
		super(arg0);
	}

	public InvalidOperationException(Throwable arg0) {
		super(arg0);
	}
} //class