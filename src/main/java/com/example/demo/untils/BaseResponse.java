package com.example.demo.untils;

public class BaseResponse {
	private int responseStatus;
	private String responseCode;
	private String message;
	private Object data;

	public BaseResponse(int responseStatus, String responseCode, String message) {
		super();
		this.responseStatus = responseStatus;
		this.responseCode = responseCode;
		this.message = message;
	}

	public BaseResponse(String message) {
		super();
		this.message = message;
	}

	public BaseResponse(int responseStatus, String responseCode, String message, Object data) {
		super();
		this.responseStatus = responseStatus;
		this.responseCode = responseCode;
		this.message = message;
		this.data = data;
	}

	public BaseResponse(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setObject(Object data) {
		this.data = data;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
