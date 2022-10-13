package com.example.ActiveMQ_JWT.service;

import com.example.ActiveMQ_JWT.entity.Role;

public interface RoleService {
	void addRole(Role role);
	Role findRoleByRoleName(String roleName);
}	
