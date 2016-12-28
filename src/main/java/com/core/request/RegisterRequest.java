/**
 * HoangAnh
 * Dec 21, 2016
 * 
 */
package com.core.request;

import java.io.Serializable;

/**
 * @author HoangAnh
 *
 */
public class RegisterRequest implements Serializable{
	private static final long serialVersionUID = -2193889568445681356L;
	private String apiToCall;
	private APIBody apiBody;
	private String method;
	public String getApiToCall() {
		return apiToCall;
	}
	public void setApiToCall(String apiToCall) {
		this.apiToCall = apiToCall;
	}
	public APIBody getApiBody() {
		return apiBody;
	}
	public void setApiBody(APIBody apiBody) {
		this.apiBody = apiBody;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
