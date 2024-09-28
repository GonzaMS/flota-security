package com.flotavehicular.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthenticationResponseDTO {
    private Long userId;
    private String token;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private String username;
    private List<String> role;
}
