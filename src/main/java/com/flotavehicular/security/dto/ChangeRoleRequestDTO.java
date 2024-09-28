package com.flotavehicular.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
public class ChangeRoleRequestDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Old role name is required")
    @NotEmpty(message = "Old role name is required")
    private String oldRoleName;

    @NotBlank(message = "New role name is required")
    @NotEmpty(message = "New role name is required")
    private String newRoleName;
}
