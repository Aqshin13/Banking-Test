package com.banking.service.inter;

import com.banking.dto.event.Event;
import com.banking.dto.request.MoneyTransferRequest;
import com.banking.dto.response.TransactionResponseDto;
import com.banking.entity.Transaction;

import java.util.List;

public interface TransactionServiceInter {


    void createTransfer(MoneyTransferRequest request,
                     Transaction.TransactionType type,
                     String topicName);


//    void createPurchase(PurchaseRequest requestDto,
//                        Transaction.TransactionType type,
//                        String topicName);

    void transfer(Event event);


    void purchase(Event event);

    void createRefund(Long id);

    void refund(Event event);

    List<TransactionResponseDto> getCustomerTransaction(Long customerId);


    }
