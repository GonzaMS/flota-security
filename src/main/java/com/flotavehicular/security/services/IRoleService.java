package com.flotavehicular.security.services;

import com.flotavehicular.security.dto.ChangeRoleRequestDTO;
import com.flotavehicular.security.dto.RoleRequestDTO;

public interface IRoleService {
    void addRole(RoleRequestDTO roleRequestDTO);

    void removeRole(RoleRequestDTO roleRequestDTO);

    void changeRole(ChangeRoleRequestDTO changeRoleRequestDTO);
}
