package com.dio.tqi.tqi_evolution_backend_2021.dto.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanDTOResponse {
    private Float value;
    private LocalDate dateFirstInstallment;
    private Integer numberInstallment;
}
