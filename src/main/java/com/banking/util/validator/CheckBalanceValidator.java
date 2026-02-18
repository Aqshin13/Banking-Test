package com.banking.util.validator;

import com.banking.dao.CustomerRepository;
import com.banking.dto.request.PurchaseRequest;
import com.banking.entity.Customer;
import com.banking.util.annotation.CheckBalance;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CheckBalanceValidator implements ConstraintValidator<CheckBalance, PurchaseRequest> {

    private final CustomerRepository repository;

    @Override
    public boolean isValid(PurchaseRequest purchaseRequest, ConstraintValidatorContext constraintValidatorContext) {
        Customer customer= repository.findById(purchaseRequest.senderId()).orElse(new Customer());
        return customer.getBalance().compareTo(purchaseRequest.amount())>=0;
    }
}
