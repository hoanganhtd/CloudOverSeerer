/**
 * HoangAnh
 * Dec 17, 2016
 * 
 */
package com.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "cos_api_info")
@JsonInclude(Include.NON_NULL)
public class APIInfo implements Serializable {
	private static final long serialVersionUID = -5022202788074031309L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonIgnore
	private Long id;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "method")
	private String method;
	
	@Column(name = "parameter")
	private String parameter;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "created_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=AppConstants.APP_FORMAT_DATE)
	private Date createdDate;
	
	
	@Column(name = "updated_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=AppConstants.APP_FORMAT_DATE)
	private Date updatedDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "apiInfo")
	private List<Schedule> schedules;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public String toString() {
		return "APIInfo [id=" + id + ", url=" + url + ", method=" + method + ", parameter=" + parameter
				+ ", uuid=" + uuid + ", status=" + status + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy + "]";
	}

	
}
