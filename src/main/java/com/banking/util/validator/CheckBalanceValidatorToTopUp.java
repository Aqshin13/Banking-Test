package com.banking.util.validator;

import com.banking.dao.CustomerRepository;
import com.banking.dto.request.PurchaseRequest;
import com.banking.dto.request.TopUpRequest;
import com.banking.entity.Customer;
import com.banking.util.annotation.CheckBalanceValidatorTopUp;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckBalanceValidatorToTopUp implements ConstraintValidator<CheckBalanceValidatorTopUp,
        TopUpRequest> {

    private final CustomerRepository repository;

    @Override
    public boolean isValid(TopUpRequest topUpRequest, ConstraintValidatorContext constraintValidatorContext) {
        Customer customer = repository.findById(topUpRequest.senderId()).orElse(new Customer());
        return customer.getBalance().compareTo(topUpRequest.amount()) >= 0;
    }
}
