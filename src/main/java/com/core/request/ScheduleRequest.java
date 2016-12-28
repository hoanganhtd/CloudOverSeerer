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
public class ScheduleRequest implements Serializable{
	private static final long serialVersionUID = 586267873860169045L;
	private String apiUUID;
	private String startJobAt;
	private String endJobAt;
	private String frequency;
	private Integer repeatCount;
	private Integer intervalTime;
	
	/************ * SETTERS AND GETTERS ********* */
	public String getApiUUID() {
		return apiUUID;
	}
	public void setApiUUID(String apiUUID) {
		this.apiUUID = apiUUID;
	}
	public String getStartJobAt() {
		return startJobAt;
	}
	public void setStartJobAt(String startJobAt) {
		this.startJobAt = startJobAt;
	}
	public String getEndJobAt() {
		return endJobAt;
	}
	public void setEndJobAt(String endJobAt) {
		this.endJobAt = endJobAt;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Integer getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}
	public Integer getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	} 
}
