/**
 * HoangAnh
 * Dec 24, 2016
 * 
 */
package com.core.model;

/**
 * @author HoangAnh
 *
 */
public enum ScheduleStatus {
	STATUS(1), PENDING(2), RUNNING(3), FINISHED(4), STOPED(5);
	
	private Integer value;
	ScheduleStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
