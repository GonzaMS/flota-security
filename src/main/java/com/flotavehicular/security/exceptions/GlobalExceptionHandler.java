package com.flotavehicular.security.exceptions;

import com.flotavehicular.security.exceptions.dto.ExceptionResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.flotavehicular.security.enums.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponseDTO.builder()
                        .status(ACCOUNT_LOCKED.getCode())
                        .message(ACCOUNT_LOCKED.getDescription())
                        .error(exp.getMessage())
                        .build());

    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponseDTO.builder()
                        .status(ACCOUNT_DISABLED.getCode())
                        .message(ACCOUNT_DISABLED.getDescription())
                        .error(exp.getMessage())
                        .build());

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponseDTO.builder()
                        .status(BAD_CREDENTIALS.getCode())
                        .message(BAD_CREDENTIALS.getDescription())
                        .error(BAD_CREDENTIALS.getDescription())
                        .build());

    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(AuthorizationDeniedException exp) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(ExceptionResponseDTO.builder()
                        .status(DENIED_AUTHORIZATION.getCode())
                        .message(DENIED_AUTHORIZATION.getDescription())
                        .error(DENIED_AUTHORIZATION.getDescription())
                        .build());

    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponseDTO.builder()
                        .error(exp.getMessage())
                        .build());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(MethodArgumentNotValidException exp) {

        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponseDTO.builder()
                        .validationErrors(errors)
                        .build());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(Exception exp) {
        log.error("An error occurred", exp);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponseDTO.builder()
                        .message("An error occurred")
                        .error(exp.getMessage())
                        .build());

    }
}
