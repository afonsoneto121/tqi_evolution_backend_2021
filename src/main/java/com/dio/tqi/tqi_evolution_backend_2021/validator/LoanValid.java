package com.dio.tqi.tqi_evolution_backend_2021.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoanValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoanValid {
    String message() default "Invalid Loan";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
