package com.flotavehicular.security.controllers;

import com.flotavehicular.security.services.impl.AuthenticationServiceImpl;
import com.proyecto.flotavehicular_webapp.dto.security.AuthenticationRequestDTO;
import com.proyecto.flotavehicular_webapp.dto.security.AuthenticationResponseDTO;
import com.proyecto.flotavehicular_webapp.dto.security.RegistrationRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequestDTO request
    ) throws MessagingException {
        authenticationServiceImpl.register(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/activate-account")
    public void activateAccount(@RequestParam String token) throws MessagingException {
        authenticationServiceImpl.activateAccount(token);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> confirm(
            @RequestBody @Valid AuthenticationRequestDTO token) {
        return ResponseEntity.ok(authenticationServiceImpl.authenticate(token));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
//        authenticationServiceImpl.logout(accessToken);
//        return ResponseEntity.ok("Logout successful");
//    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String token) {
        boolean isValid = authenticationServiceImpl.validateToken(token);
        return ResponseEntity.ok(isValid);
    }

}
