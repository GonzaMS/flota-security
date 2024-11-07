package com.flotavehicular.security.repositories;

import com.proyecto.flotavehicular_webapp.models.Security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findAllByEnabled(boolean enabled, Pageable pageable);

}
