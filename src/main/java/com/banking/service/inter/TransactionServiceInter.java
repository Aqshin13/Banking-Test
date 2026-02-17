package com.banking.service.inter;

import com.banking.dto.event.Event;
import com.banking.dto.request.PurchaseRequest;
import com.banking.dto.request.TopUpRequest;
import com.banking.entity.Transaction;

public interface TransactionServiceInter {


    void createTopUp(TopUpRequest requestDto,
                     Transaction.TransactionType type,
                     String topicName);


    void createPurchase(PurchaseRequest requestDto,
                        Transaction.TransactionType type,
                        String topicName);

    void transfer(Event event);


    void purchase(Event event);

    void createRefund(Long id);

    void refund(Event event);

}
