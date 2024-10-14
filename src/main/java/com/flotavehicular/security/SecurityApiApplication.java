package com.flotavehicular.security;

import com.flotavehicular.security.repositories.IRoleRepository;
import com.proyecto.flotavehicular_webapp.models.Security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EntityScan(basePackages = {"com.proyecto.flotavehicular_webapp.models.Security"})
public class SecurityApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(IRoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(Role.builder().name("ROLE_USER").build());
            }
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
            }
            if (roleRepository.findByName("ROLE_DRIVER").isEmpty()) {
                roleRepository.save(Role.builder().name("ROLE_DRIVER").build());
            }
        };
    }

}
