package com.fabianbah.auth_server.services.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabianbah.auth_server.entities.Role;
import com.fabianbah.auth_server.repositories.RoleRepository;
import com.fabianbah.auth_server.services.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role newRole) {
        return roleRepository.save(newRole);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("El role consultado no existe"));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRoleById(String roleName) {
        Role role = this.getRoleByName(roleName);

        roleRepository.delete(role);
    }

}
