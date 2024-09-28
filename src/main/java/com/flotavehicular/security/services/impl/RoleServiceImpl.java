package com.flotavehicular.security.services.impl;

import com.flotavehicular.security.dto.ChangeRoleRequestDTO;
import com.flotavehicular.security.dto.RoleRequestDTO;
import com.flotavehicular.security.models.Role;
import com.flotavehicular.security.models.User;
import com.flotavehicular.security.repositories.IRoleRepository;
import com.flotavehicular.security.repositories.IUserRepository;
import com.flotavehicular.security.services.IRoleService;
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
        User user = userRepository.findById(roleRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleRequestDTO.getRoleName()).orElseThrow(() -> new RuntimeException("Role not found only Driver, User or Admin roles are allowed"));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }else {
            throw new RuntimeException("User already has the role");
        }

        userRepository.save(user);
    }

    @Override
    public void removeRole(RoleRequestDTO roleRequestDTO) {
        User user = userRepository.findById(roleRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleRequestDTO.getRoleName()).orElseThrow(() -> new RuntimeException("Role not found only Driver, User or Admin roles are allowed"));

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        }else {
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

        if (user.getRoles().contains(oldRole)) {
            user.getRoles().remove(oldRole);
        } else {
            throw new RuntimeException("User does not have the old role");
        }

        if (!user.getRoles().contains(newRole)) {
            user.getRoles().add(newRole);
        }

        userRepository.save(user);
    }
}
