package com.dio.tqi.tqi_evolution_backend_2021.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginDTORequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
