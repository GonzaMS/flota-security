package com.flotavehicular.security.controllers;

import com.flotavehicular.security.services.IRoleService;
import com.proyecto.flotavehicular_webapp.dto.security.ChangeRoleRequestDTO;
import com.proyecto.flotavehicular_webapp.dto.security.RoleRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> addRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        roleService.addRole(roleRequestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("addRole", "Role added successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> removeRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        roleService.removeRole(roleRequestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("removeRole", "Role removed successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> changeUserRole(@RequestBody @Valid ChangeRoleRequestDTO changeRoleRequestDTO) {
        roleService.changeRole(changeRoleRequestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("changeRole", "User role changed successfully");

        return ResponseEntity.ok(response);
    }
}
