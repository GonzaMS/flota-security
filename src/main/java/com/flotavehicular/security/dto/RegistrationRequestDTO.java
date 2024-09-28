package com.flotavehicular.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class RegistrationRequestDTO {

    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name cannot be blank")
    private String firstname;

    @NotEmpty(message = "Last name is required")
    @NotBlank(message = "Last name cannot be blank")
    private String lastname;

    @NotEmpty(message = "Username is required")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    @NotEmpty(message = "Email is required")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid example google@gmail.com")
    private String email;

    @NotEmpty(message = "Date of birth is required")
    @NotBlank(message = "Date of birth cannot be blank")
    private Date dateOfBirth;
}
