package com.dio.tqi.tqi_evolution_backend_2021.dto.request;

import com.dio.tqi.tqi_evolution_backend_2021.validator.LoanValid;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@LoanValid
public class LoanDTORequest {
    @NotNull
    private Float value;
    @NotNull
    private LocalDate dateFirstInstallment;
    @Min(0)
    @Max(60)
    @NotNull
    private Integer numberInstallment;

}
