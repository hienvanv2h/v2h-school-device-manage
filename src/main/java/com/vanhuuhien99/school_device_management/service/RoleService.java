package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByName(String roleName);

    List<Role> getAllRoles();
}
