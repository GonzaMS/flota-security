package com.flotavehicular.security.controllers;

import com.flotavehicular.security.services.IRoleService;
import com.proyecto.flotavehicular_webapp.dto.security.ChangeRoleRequestDTO;
import com.proyecto.flotavehicular_webapp.dto.security.RoleRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        roleService.addRole(roleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        roleService.removeRole(roleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeUserRole(@RequestBody @Valid ChangeRoleRequestDTO changeRoleRequestDTO) {
        roleService.changeRole(changeRoleRequestDTO);
        return ResponseEntity.ok().build();
    }
}
