package com.banking.util.annotation;

import com.banking.util.validator.CheckBalanceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
        validatedBy = {CheckBalanceValidator.class}
)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckBalance {

    String message() default "Balans kifayet qeder deyil";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
