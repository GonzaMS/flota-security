package com.flotavehicular.security.services.impl;

import com.flotavehicular.security.repositories.IRoleRepository;
import com.flotavehicular.security.repositories.IUserRepository;
import com.flotavehicular.security.services.IRoleService;
import com.proyecto.flotavehicular_webapp.dto.security.ChangeRoleRequestDTO;
import com.proyecto.flotavehicular_webapp.dto.security.RoleRequestDTO;
import com.proyecto.flotavehicular_webapp.models.Security.Role;
import com.proyecto.flotavehicular_webapp.models.Security.User;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    public RoleServiceImpl(IRoleRepository roleRepository, IUserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addRole(RoleRequestDTO roleRequestDTO) {
        User user = userRepository.findById(roleRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleRequestDTO.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found only Driver, User or Admin roles are allowed"));

        if (user.getRoles().stream().noneMatch(r -> r.getName().equals(role.getName()))) {
            user.getRoles().add(role);
        } else {
            throw new RuntimeException("User already has the role");
        }

        userRepository.save(user);
    }

    @Override
    public void removeRole(RoleRequestDTO roleRequestDTO) {
        User user = userRepository.findById(roleRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleRequestDTO.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found only Driver, User or Admin roles are allowed"));

        boolean removed = user.getRoles().removeIf(r -> r.getName().equals(role.getName()));
        if (!removed) {
            throw new RuntimeException("User does not have the role");
        }

        userRepository.save(user);
    }

    @Override
    public void changeRole(ChangeRoleRequestDTO changeRoleRequestDTO) {
        User user = userRepository.findById(changeRoleRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role oldRole = roleRepository.findByName(changeRoleRequestDTO.getOldRoleName())
                .orElseThrow(() -> new RuntimeException("Old role not found"));

        Role newRole = roleRepository.findByName(changeRoleRequestDTO.getNewRoleName())
                .orElseThrow(() -> new RuntimeException("New role not found"));

        boolean removed = user.getRoles().removeIf(r -> r.getName().equals(oldRole.getName()));
        if (!removed) {
            throw new RuntimeException("User does not have the old role");
        }

        if (user.getRoles().stream().noneMatch(r -> r.getName().equals(newRole.getName()))) {
            user.getRoles().add(newRole);
        }

        userRepository.save(user);
    }
}
