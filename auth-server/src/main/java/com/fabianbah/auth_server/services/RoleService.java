package com.fabianbah.auth_server.services;

import java.util.List;

import com.fabianbah.auth_server.entities.Role;

public interface RoleService {
    Role createRole(Role newRole);

    Role getRoleByName(String roleName);

    List<Role> getAllRoles();

    void deleteRoleById(String roleName);
}
