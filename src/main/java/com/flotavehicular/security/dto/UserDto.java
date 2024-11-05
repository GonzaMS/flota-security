package com.flotavehicular.security.dto;

import com.proyecto.flotavehicular_webapp.models.Security.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDto  implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long userId;
    private String fullName;
    private Boolean active;
    private List<Role> roles;
}
