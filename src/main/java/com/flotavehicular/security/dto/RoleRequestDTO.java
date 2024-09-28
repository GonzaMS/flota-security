package com.flotavehicular.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleRequestDTO {

    @NotNull(message = "Id is required")
    private Long userId;

    @NotNull(message = "Role name is required")
    @NotBlank(message = "Role name is required")
    private String roleName;
}
