package com.banking.util.annotation;

import com.banking.util.validator.CheckBalanceValidatorToPurchase;
import com.banking.util.validator.CheckBalanceValidatorToTopUp;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



import com.banking.util.validator.CheckBalanceValidatorToPurchase;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
        validatedBy = {CheckBalanceValidatorToTopUp.class}
)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckBalanceValidatorTopUp {

    String message() default "Balans kifayet qeder deyil";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
