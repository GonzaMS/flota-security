package com.flotavehicular.security.repositories;

import com.flotavehicular.security.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
