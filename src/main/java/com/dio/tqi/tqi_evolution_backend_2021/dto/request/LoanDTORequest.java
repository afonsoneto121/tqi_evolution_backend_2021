package com.dio.tqi.tqi_evolution_backend_2021.dto.request;

import com.dio.tqi.tqi_evolution_backend_2021.validator.LoanValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@LoanValid
public class LoanDTORequest {
    @NotNull
    @Min(1)
    private Float value;
    private LocalDate dateFirstInstallment;
    @Min(0)
    @Max(60)
    @NotNull
    private Integer numberInstallment;

}
