package com.flotavehicular.security.services;


import com.proyecto.flotavehicular_webapp.dto.security.ChangeRoleRequestDTO;
import com.proyecto.flotavehicular_webapp.dto.security.RoleRequestDTO;

public interface IRoleService {
    void addRole(RoleRequestDTO roleRequestDTO);

    void removeRole(RoleRequestDTO roleRequestDTO);

    void changeRole(ChangeRoleRequestDTO changeRoleRequestDTO);
}
