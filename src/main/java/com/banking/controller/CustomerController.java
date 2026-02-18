package com.banking.controller;


import com.banking.dto.request.CustomerSaveRequest;
import com.banking.dto.request.MoneyTransferRequest;
import com.banking.dto.request.PurchaseRequest;
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
    public ResponseEntity<GenericResponse> save(@RequestBody @Valid CustomerSaveRequest request ){
        customerServiceInter.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse("Customer is created"));
    }



    @PostMapping("/money/transfer")
    public ResponseEntity<GenericResponse> topUp(@RequestBody @Valid MoneyTransferRequest request ){
        transactionServiceInter.createTransfer(request, Transaction.TransactionType.TOP_UP,
                "created-topup-topic");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse("Your  request is being processed"));
    }


    @GetMapping("/{id}/balance")
    public ResponseEntity<GenericResponse> topUp(@PathVariable Long id ){

       BigDecimal balance= customerServiceInter.getBalance(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse("Balance: "+balance));
    }

    @PostMapping("/purchase")
    public ResponseEntity<GenericResponse> purchase(@RequestBody @Valid MoneyTransferRequest request ){
        transactionServiceInter.createTransfer(request, Transaction.TransactionType.PURCHASE,
                "created-purchase-topic");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse("Your  request is being processed"));
    }



    @PostMapping("/refund/purchase/{id}")
    public ResponseEntity<GenericResponse> refund(@PathVariable Long id){
        transactionServiceInter.createRefund(id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponse("Your  request is being processed"));
    }


    @GetMapping("/{id}/transactions")
    public List<TransactionResponseDto> getCustomerTransaction(@PathVariable Long id){
        return transactionServiceInter.getCustomerTransaction(id);
    }




}
