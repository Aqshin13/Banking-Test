package com.banking.service.impl;


import com.banking.config.TopicName;
import com.banking.dao.CustomerRepository;
import com.banking.dao.TransactionRepository;
import com.banking.dto.event.Event;
import com.banking.dto.request.MoneyTransferRequest;
import com.banking.dto.response.TransactionResponseDto;
import com.banking.entity.Customer;
import com.banking.entity.Transaction;
import com.banking.exception.CustomerNotFound;
import com.banking.exception.TransactionNotFound;
import com.banking.service.inter.TransactionServiceInter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionServiceInter {

    private final CustomerRepository repository;
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, Event> producer;


    private void process(Event event) {
        Customer sender = repository.findById(event.senderId())
                .orElseThrow(() -> new CustomerNotFound("Sender is not found"));
        Customer receiver = repository.findById(event.receiverId())
                .orElseThrow(() -> new CustomerNotFound("Receiver is not found"));
        Transaction transaction = transactionRepository
                .findById(event.transactionId()).orElseThrow(()->new TransactionNotFound("Transation is not found"));
        sender.setBalance(sender.getBalance().subtract(event.amount()));
        receiver.setBalance(receiver.getBalance().add(event.amount()));
        repository.save(sender);
        repository.save(receiver);
        transaction
                .setStatus(Transaction.TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);
    }


    @Override
    public void createTransfer(MoneyTransferRequest requestDto
            , Transaction.TransactionType type, String topicName) {
        Transaction transaction = new Transaction();
        transaction.setReceiver(requestDto.receiverId());
        transaction.setSender(requestDto.senderId());
        transaction.setAmount(requestDto.amount());
        transaction.setStatus(Transaction.TransactionStatus.PROCESSING);
        transaction.setType(type);
        Transaction transactionDB = transactionRepository.save(transaction);
        Event topUpCreateEvent = new Event(
                transactionDB.getId(),
                transactionDB.getSender(),
                transactionDB.getReceiver(),
                transactionDB.getAmount()
        );
        System.out.println(transactionDB.getId());
        producer.send(topicName, topUpCreateEvent);
    }


    @Override
    public void createRefund(Long id) {
        Transaction transaction =
                transactionRepository.getTransaction(id).orElseThrow(
                        () -> new TransactionNotFound("Transaction is not found")
                );
        Transaction transactionNew = new Transaction();
        transactionNew.setReceiver(transaction.getSender());
        transactionNew.setSender(transaction.getReceiver());
        transactionNew.setAmount(
                transaction.getAmount().multiply(BigDecimal.valueOf(0.3))
        );
        transactionNew.setStatus(Transaction.TransactionStatus.PROCESSING);
        transactionNew.setType(Transaction.TransactionType.REFUND);
        transactionNew.setPurchase(transaction);
        Transaction transactionDB = transactionRepository.save(transactionNew);
        transaction.setRefund(true);
        transactionRepository.save(transaction);
        Event refundEvent = new Event(
                transactionDB.getId(),
                transactionDB.getSender(),
                transactionDB.getReceiver(),
                transactionDB.getAmount()
        );
        producer.send(TopicName.REFUND.getName(), refundEvent);


    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public void purchase(Event event) {
        process(event);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void transfer(Event event) {
        process(event);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void refund(Event event) {
        process(event);
    }


    public List<TransactionResponseDto> getCustomerTransaction(Long customerId) {
        return transactionRepository.findCustomerTransaction(customerId).stream()
                .map(x -> new TransactionResponseDto(
                        x.getId(), x.getSender(), x.getReceiver(), x.getType(), x.getAmount(),
                        x.getStatus(), x.isRefund()
                )).toList();
    }


}
