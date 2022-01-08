package com.dio.tqi.tqi_evolution_backend_2021.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTOResponse {
    private String id;
    private Float value;
    private LocalDate dateFirstInstallment;
    private Integer numberInstallment;
}
