package com.rohat.service.common.authenticaiton;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	protected Long version;

	@CreationTimestamp
	protected LocalDateTime createdOn;

	@UpdateTimestamp
	protected LocalDateTime updatedOn;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
}