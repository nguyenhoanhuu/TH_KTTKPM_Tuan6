package com.example.ActiveMQ_JWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ActiveMQ_JWT.entity.Role;
import com.example.ActiveMQ_JWT.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void addRole(Role role) {
		roleRepository.save(role);
		
	}

	@Override
	public Role findRoleByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findRoleByRoleName(roleName);
	}
}
