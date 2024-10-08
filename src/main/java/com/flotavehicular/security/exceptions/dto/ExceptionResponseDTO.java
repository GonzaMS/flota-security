package com.flotavehicular.security.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponseDTO {
    private int status;
    private String message;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;
}
