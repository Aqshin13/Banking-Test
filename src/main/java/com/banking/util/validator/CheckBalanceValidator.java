package com.banking.util.validator;

import com.banking.dao.CustomerRepository;
import com.banking.dto.request.MoneyTransferRequest;
import com.banking.entity.Customer;
import com.banking.util.annotation.CheckBalance;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CheckBalanceValidator implements ConstraintValidator<CheckBalance, MoneyTransferRequest> {

    private final CustomerRepository repository;

    @Override
    public boolean isValid(MoneyTransferRequest transfer, ConstraintValidatorContext constraintValidatorContext) {
        Customer customer= repository.findById(transfer.senderId()).orElse(new Customer());
        return customer.getBalance().compareTo(transfer.amount())>=0;
    }
}
