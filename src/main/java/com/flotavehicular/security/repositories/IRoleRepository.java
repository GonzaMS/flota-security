package com.flotavehicular.security.repositories;

import com.proyecto.flotavehicular_webapp.models.Security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String role);
}
