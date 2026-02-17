package com.banking.util.validator;

import com.banking.dao.CustomerRepository;
import com.banking.util.annotation.IsThereCustomer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class IsThereSenderValidator implements ConstraintValidator<IsThereCustomer,Long> {


    private final CustomerRepository repository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return !repository.findById(id).isEmpty();
    }
}
