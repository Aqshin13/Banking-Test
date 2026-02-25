package com.banking.controller;


import com.banking.config.TopicName;
import com.banking.dto.request.MoneyTransferRequest;
import com.banking.dto.response.GenericResponse;
import com.banking.dto.response.TransactionResponseDto;
import com.banking.entity.Transaction;
import com.banking.service.inter.TransactionServiceInter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceInter transactionServiceInter;


    @PostMapping("/money/transfer")
    public ResponseEntity<GenericResponse> topUp(@RequestBody @Valid MoneyTransferRequest request) {
        transactionServiceInter.createTransfer(request,
                Transaction.TransactionType.TOP_UP,
                TopicName.TOPUP_TOPIC.getName());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new GenericResponse("Your  request is being processed"));
    }


    @PostMapping("/purchase")
    public ResponseEntity<GenericResponse> purchase(@RequestBody @Valid MoneyTransferRequest request) {
        transactionServiceInter.createTransfer(request,
                Transaction.TransactionType.PURCHASE,
                TopicName.PURCHASE.getName());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new GenericResponse("Your  request is being processed"));
    }


    @PostMapping("/refund/purchase/{id}")
    public ResponseEntity<GenericResponse> refund(@PathVariable Long id) {
        transactionServiceInter.createRefund(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new GenericResponse("Your  request is being processed"));
    }





}
