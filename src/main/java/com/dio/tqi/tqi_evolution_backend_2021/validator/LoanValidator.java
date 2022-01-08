package com.dio.tqi.tqi_evolution_backend_2021.validator;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.LoanDTORequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class LoanValidator implements ConstraintValidator<LoanValid, LoanDTORequest> {
    @Override
    public boolean isValid(LoanDTORequest value, ConstraintValidatorContext context) {
//        LocalDate dateFirstInstallment = value.getDateFirstInstallment().plusMonths(3);
//        return dateFirstInstallment.isBefore(LocalDate.now());
        return value == null ? false:
                value.getDateFirstInstallment()
                .minusMonths(3)
                .isBefore(LocalDate.now());
    }

}
