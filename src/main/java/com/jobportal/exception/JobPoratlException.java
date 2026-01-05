package com.jobportal.exception;

public class JobPoratlException extends Exception{
	public JobPoratlException(String message ,Throwable cause) {
		super(message,cause);
	}
	public JobPoratlException(String message) {
		super(message);
	}

}
