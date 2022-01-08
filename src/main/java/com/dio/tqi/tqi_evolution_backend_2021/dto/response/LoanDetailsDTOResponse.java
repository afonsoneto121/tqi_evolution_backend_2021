package com.dio.tqi.tqi_evolution_backend_2021.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetailsDTOResponse {
    private String id;
    private Float value;
    private LocalDate dateFirstInstallment;
    private Integer numberInstallment;
    private String email;
    private float income;
}
