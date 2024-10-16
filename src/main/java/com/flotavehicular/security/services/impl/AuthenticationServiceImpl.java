package com.flotavehicular.security.services.impl;

import com.flotavehicular.security.config.ActivationUrlEmail;
import com.flotavehicular.security.config.JwtService;
import com.flotavehicular.security.enums.EmailTemplateName;
import com.flotavehicular.security.repositories.IRoleRepository;
import com.flotavehicular.security.repositories.ITokenRepository;
import com.flotavehicular.security.repositories.IUserRepository;
import com.proyecto.flotavehicular_webapp.dto.security.AuthenticationRequestDTO;
import com.proyecto.flotavehicular_webapp.dto.security.AuthenticationResponseDTO;
import com.proyecto.flotavehicular_webapp.dto.security.RegistrationRequestDTO;
import com.proyecto.flotavehicular_webapp.models.Security.Role;
import com.proyecto.flotavehicular_webapp.models.Security.Token;
import com.proyecto.flotavehicular_webapp.models.Security.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl {

    private final IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final IUserRepository userRepository;

    private final ITokenRepository tokenRepository;

    private final EmailServiceImpl emailService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final ActivationUrlEmail activationUrlEmail;

//    @Value("${activation.url}")
//    private String activationUrl;

    public void register(RegistrationRequestDTO request) throws MessagingException {
        var userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role not found"));
        log.info("User role: {}", userRole);

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .dateOfBirth(request.getDate_of_birth())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .accountNonLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);

    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateSaveActivationToken(user);

        String dynamicActivationUrl = activationUrlEmail.getDynamicActivationUrl("security-microservice");

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                dynamicActivationUrl,
                newToken,
                "Activate your account"
        );

    }

    private String generateSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateActivationCode(int length) {
        // Generate a random code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length()); // 0..9
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .userId(user.getId())
                .enabled(user.isEnabled())
                .accountNonLocked(user.isAccountNonLocked())
                .username(user.getUsername())
                .role(user.getRoles().stream()
                        .map(Role::getName).toList())
                .build();
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new one has been sent to your email");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    public boolean validateToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            String username = jwtService.extractUsername(token);

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return jwtService.isTokenValid(token, user);

        } catch (Exception e) {
            log.error("Error during token validation: {}", e.getMessage());
            return false;
        }
    }

}
