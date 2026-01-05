package com.jobportal.exception;

public class DataAccessException extends ApplicationException {
		public DataAccessException(String message,Throwable cause) {
			super(message);
			initCause(cause);
		}
}
