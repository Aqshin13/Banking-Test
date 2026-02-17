package com.banking.util.annotation;


import com.banking.util.validator.IsThereSenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsThereSenderValidator.class}
)
public @interface IsThereCustomer {

    String message() default "Customer is not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
