/**
 * HoangAnh
 * Dec 17, 2016
 * 
 */
package com.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author HoangAnh
 *
 */
@JsonInclude(Include.NON_NULL)
public class AppResponse {
	public static final String FAILURE = "FAILURE";
	public static final String SUCCESS = "SUCCESS";
	private String status;
	private String message;
	private Object response;
	
	/**
	 * 
	 */
	public AppResponse() {
		this.message = "";
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
}
