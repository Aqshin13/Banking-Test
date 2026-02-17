package com.banking.config;


import com.banking.dao.TransactionRepository;
import com.banking.dto.event.Event;
import com.banking.entity.Transaction;
import com.banking.service.inter.CustomerServiceInter;
import com.banking.service.inter.TransactionServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyKafkaConsumer {

    private final TransactionRepository transactionRepository;
    private final TransactionServiceInter transactionServiceInter;

    @KafkaListener(
            topics = "created-topup-topic"
            , groupId = "consumer-group"
    )
    public void transferMoney(Event event) {
        try {
            transactionServiceInter.transfer(event);
        } catch (Exception exception) {
            onFAILED(event);

        }
    }


    @KafkaListener(
            topics = "created-purchase-topic"
            , groupId = "consumer-group"
    )
    public void purchase(Event event) {
        try {
            transactionServiceInter.purchase(event);
        } catch (Exception exception) {
            onFAILED(event);

        }
    }


    @KafkaListener(
            topics = "created-refund-topic"
            , groupId = "consumer-group"
    )
    public void refund(Event event) {
        try {
            transactionServiceInter.refund(event);
        } catch (Exception exception) {
            onFAILED(event);

        }
    }


    private void onFAILED(Event event) {
        Transaction transaction = transactionRepository
                .findById(event.transactionId()).orElse(new Transaction());
        transaction
                .setStatus(Transaction.TransactionStatus.FAILED);
        transactionRepository.save(transaction);
    }

}
