package com.jobportal.model;

public class ApiResponse {
	private boolean success;
	private String message;
	private String redirectUrl;
	public ApiResponse() {}
	
	public ApiResponse(boolean success,String message) {
		this.success=success;
		this.message=message;
	}
	
	public ApiResponse(boolean b, String m, String redirectUrl) {
		success=b;
		message=m;
		this.redirectUrl=redirectUrl;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success=success;
	}
	
	public void setMessage(String message) {
		this.message=message;
	}
	public String getMessage() {
		return message;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
