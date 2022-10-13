package com.example.ActiveMQ_JWT.entity;

public class RoleToUserFrom {
	private String username;
	private String roleName;
	private String roleKey;
	public RoleToUserFrom(String username, String roleName, String roleKey) {
		super();
		this.username = username;
		this.roleName = roleName;
		this.roleKey = roleKey;
	}
	public RoleToUserFrom() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	
	
	
}
