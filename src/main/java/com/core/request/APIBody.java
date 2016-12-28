/**
 * HoangAnh
 * Dec 21, 2016
 * 
 */
package com.core.request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HoangAnh
 *
 */
public class APIBody {
	private String status;
	private List<String> instanceIds = new ArrayList<String>();
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getInstanceIds() {
		return instanceIds;
	}
	public void setInstanceIds(List<String> instanceIds) {
		this.instanceIds = instanceIds;
	}
	
}
