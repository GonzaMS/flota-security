package com.flotavehicular.security.services.impl;

import com.flotavehicular.security.repositories.IRoleRepository;
import com.flotavehicular.security.repositories.IUserRepository;
import com.flotavehicular.security.services.IRoleService;
import com.proyecto.flotavehicular_webapp.dto.security.ChangeRoleRequestDTO;
import com.proyecto.flotavehicular_webapp.dto.security.RoleRequestDTO;
import com.proyecto.flotavehicular_webapp.models.Security.Role;
import com.proyecto.flotavehicular_webapp.models.Security.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    public RoleServiceImpl(IRoleRepository roleRepository, IUserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found; only Driver, User, or Admin roles are allowed"));
    }

    @Override
    public void addRole(RoleRequestDTO roleRequestDTO) {
        User user = getUserById(roleRequestDTO.getUserId());
        Role role = getRoleByName(roleRequestDTO.getRoleName());

        if (userHasRole(user, role)) {
            throw new RuntimeException("User already has the role");
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRole(RoleRequestDTO roleRequestDTO) {
        User user = getUserById(roleRequestDTO.getUserId());
        Role roleToRemove = getRoleByName(roleRequestDTO.getRoleName());

        boolean removed = user.getRoles().removeIf(r -> Objects.equals(r.getName(), roleToRemove.getName()));
        if (!removed) {
            throw new RuntimeException("User does not have the role");
        }

        userRepository.save(user);
    }

    @Override
    public void changeRole(ChangeRoleRequestDTO changeRoleRequestDTO) {
        User user = getUserById(changeRoleRequestDTO.getUserId());
        Role oldRole = getRoleByName(changeRoleRequestDTO.getOldRoleName());
        Role newRole = getRoleByName(changeRoleRequestDTO.getNewRoleName());

        if (!user.getRoles().removeIf(r -> Objects.equals(r.getName(), oldRole.getName()))) {
            throw new RuntimeException("User does not have the old role");
        }

        if (!userHasRole(user, newRole)) {
            user.getRoles().add(newRole);
        }

        userRepository.save(user);
    }

    private boolean userHasRole(User user, Role role) {
        return user.getRoles().stream().anyMatch(r -> Objects.equals(r.getName(), role.getName()));
    }
}
