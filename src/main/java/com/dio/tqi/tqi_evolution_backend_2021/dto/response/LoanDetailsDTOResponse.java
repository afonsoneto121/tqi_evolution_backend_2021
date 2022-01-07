package com.dio.tqi.tqi_evolution_backend_2021.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LoanDetailsDTOResponse {
    private String id;
    private Float value;
    private LocalDate dateFirstInstallment;
    private Integer numberInstallment;
    private String email;
    private float income;
}
