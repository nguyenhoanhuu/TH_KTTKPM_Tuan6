package com.example.ActiveMQ_JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ActiveMQ_JWT.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findRoleByRoleName(String roleName);
}
