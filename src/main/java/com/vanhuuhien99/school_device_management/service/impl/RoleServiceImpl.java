package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.Role;
import com.vanhuuhien99.school_device_management.repository.RoleRepository;
import com.vanhuuhien99.school_device_management.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
