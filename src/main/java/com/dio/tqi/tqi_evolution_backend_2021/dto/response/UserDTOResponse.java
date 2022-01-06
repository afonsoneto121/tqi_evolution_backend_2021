package com.dio.tqi.tqi_evolution_backend_2021.dto.response;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDTOResponse {
    private String id;
    private String name;
    private String email;
    private String cpf;
    private String rg;
    private String address;
    private Float income;
}
