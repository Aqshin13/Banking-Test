package com.banking.controller;


import com.banking.config.TopicName;
import com.banking.dto.request.CustomerSaveRequest;
import com.banking.dto.request.MoneyTransferRequest;
import com.banking.dto.response.GenericResponse;
import com.banking.dto.response.TransactionResponseDto;
import com.banking.entity.Transaction;
import com.banking.service.inter.CustomerServiceInter;
import com.banking.service.inter.TransactionServiceInter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerServiceInter customerServiceInter;
    private final TransactionServiceInter transactionServiceInter;


    @PostMapping
    public ResponseEntity<GenericResponse> save(@RequestBody @Valid CustomerSaveRequest request) {
        customerServiceInter.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse("Customer is created"));
    }


    @GetMapping("/{id}/transactions")
    public List<TransactionResponseDto> getCustomerTransaction(@PathVariable Long id) {
        return transactionServiceInter.getCustomerTransaction(id);
    }


    @GetMapping("/{id}/balance")
    public ResponseEntity<GenericResponse> topUp(@PathVariable Long id) {

        BigDecimal balance = customerServiceInter.getBalance(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse("Balance: " + balance));
    }



}
