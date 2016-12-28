/**
 * HoangAnh
 * Dec 17, 2016
 * 
 */
package com.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.core.configs.AppConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author HoangAnh
 *
 */

@Entity
@Table(name = "cos_schedule")
@JsonInclude(Include.NON_NULL)
public class Schedule implements Serializable {
	private static final long serialVersionUID = -5022202788074031309L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonIgnore
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="start_job_at")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=AppConstants.APP_FORMAT_DATE)
	private Date startJobAt;
	
	@Column(name="end_job_at")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=AppConstants.APP_FORMAT_DATE)
	private Date endJobAt;
	
	@Column(name = "frequency")
	private String frequency;
	
	@Column(name = "repeat_count")
	private Integer repeatCount;
	
	@Column(name = "interval_time")
	private Integer intervalTime; 
	
	@Column(name = "created_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=AppConstants.APP_FORMAT_DATE)
	private Date createdDate;
	
	@Column(name = "updated_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=AppConstants.APP_FORMAT_DATE)
	private Date updatedDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "executed_count")
	private Integer executedCount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "api_info_id", nullable = false)
	@JsonIgnore
	private APIInfo apiInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public APIInfo getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(APIInfo apiInfo) {
		this.apiInfo = apiInfo;
	}

	public Date getEndJobAt() {
		return endJobAt;
	}

	public void setEndJobAt(Date endJobAt) {
		this.endJobAt = endJobAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getStartJobAt() {
		return startJobAt;
	}

	public void setStartJobAt(Date startJobAt) {
		this.startJobAt = startJobAt;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getExecutedCount() {
		return executedCount;
	}

	public void setExecutedCount(Integer executedCount) {
		this.executedCount = executedCount;
	}
	
	public void increaseExecutedCount() {
		if (executedCount == null || executedCount == 0) {
			executedCount = 1;
		} else {
			executedCount++;
		}
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", name=" + name + ", startJobAt=" + startJobAt + ", endJobAt=" + endJobAt
				+ ", frequency=" + frequency + ", repeatCount=" + repeatCount + ", intervalTime=" + intervalTime
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", status=" + status + ", uuid="
				+ uuid + ", createdBy=" + createdBy + ", executedCount=" + executedCount + ", apiInfo=" + apiInfo + "]";
	}
}
