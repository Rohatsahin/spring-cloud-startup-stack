package com.rohat.service.common.authenticaiton;

import javax.persistence.Entity;

@Entity
public class Permission extends BaseIdEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}