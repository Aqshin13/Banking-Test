package com.banking.service.impl;

import com.banking.dao.CustomerRepository;
import com.banking.dao.TransactionRepository;
import com.banking.dto.request.MoneyTransferRequest;
import com.banking.dto.event.Event;
import com.banking.dto.request.CustomerSaveRequest;
import com.banking.dto.request.PurchaseRequest;
import com.banking.dto.request.TopUpRequest;
import com.banking.entity.Customer;
import com.banking.entity.Transaction;
import com.banking.exception.CustomerNotFound;
import com.banking.service.inter.CustomerServiceInter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerServiceInter {
    private final CustomerRepository repository;
    private final TransactionRepository transactionRepository;

    @Override
    public void save(CustomerSaveRequest request) {
        repository.save(Customer.builder()
                .name(request.name())
                .surname(request.surname())
                .birthDate(request.birthDate())
                .gsmNumber(request.gsmNumber())
                .balance(BigDecimal.valueOf(100))
                .build());

    }

    @Override
    public BigDecimal getBalance(Long id) {
       Customer customer= repository.findById(id)
               .orElseThrow(()-> new CustomerNotFound("Customer is not found"));

      return customer.getBalance();

    }


}
