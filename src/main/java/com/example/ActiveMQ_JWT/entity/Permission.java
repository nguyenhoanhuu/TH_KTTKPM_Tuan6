package com.example.ActiveMQ_JWT.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_permission")
@Getter
@Setter
public class Permission extends BaseEntity {

    private String permissionName;

    private String permissionKey;

	public Permission(Long id, String deleted, Date createdAt, Date updatedAt, Long createdBy, Long updatedBy,
			String permissionName, String permissionKey) {
		super(id, deleted, createdAt, updatedAt, createdBy, updatedBy);
		this.permissionName = permissionName;
		this.permissionKey = permissionKey;
	}

	public Permission(Long id, String deleted, Date createdAt, Date updatedAt, Long createdBy, Long updatedBy) {
		super(id, deleted, createdAt, updatedAt, createdBy, updatedBy);
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}
    
    

}