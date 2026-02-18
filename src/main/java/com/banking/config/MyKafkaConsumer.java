    package com.banking.config;


    import com.banking.dao.TransactionRepository;
    import com.banking.dto.event.Event;
    import com.banking.entity.Transaction;
    import com.banking.service.inter.CustomerServiceInter;
    import com.banking.service.inter.TransactionServiceInter;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.kafka.annotation.BackOff;
    import org.springframework.kafka.annotation.DltHandler;
    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.kafka.annotation.RetryableTopic;
    import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
    import org.springframework.kafka.support.KafkaHeaders;
    import org.springframework.messaging.handler.annotation.Header;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class MyKafkaConsumer {

        private final TransactionRepository transactionRepository;
        private final TransactionServiceInter transactionServiceInter;


        @RetryableTopic(
                attempts = "4",
                backOff = @BackOff(delay = 2000, multiplier = 1.5, maxDelay = 10000),
                autoCreateTopics = "true",
                topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
        )
        @KafkaListener(
                topics = "created-topup-topic"
                , groupId = "consumer-group"
        )
        public void transferMoney(Event event) {
                transactionServiceInter.transfer(event);
        }


        @RetryableTopic(
                attempts = "4",
                backOff = @BackOff(delay = 2000, multiplier = 1.5, maxDelay = 10000),
                autoCreateTopics = "true",
                topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
        )
        @KafkaListener(
                topics = "created-purchase-topic"
                , groupId = "consumer-group"
        )
        public void purchase(Event event) {
                transactionServiceInter.purchase(event);
        }


        @RetryableTopic(
                attempts = "4",
                backOff = @BackOff(delay = 2000, multiplier = 1.5, maxDelay = 10000),
                autoCreateTopics = "true",
                topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
        )
        @KafkaListener(
                topics = "created-refund-topic"
                , groupId = "consumer-group"
        )
        public void refund(Event event) {
                transactionServiceInter.refund(event);
        }


        @DltHandler
        public void handleDlt(Event event, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
            if (topic.contains("topup")) {
                log.info("Top-up DLQ: {}", event);
            } else if (topic.contains("purchase")) {
                log.info("Purchase DLQ: {}", event);
            } else if (topic.contains("refund")) {
                log.info("Refund DLQ: {}", event);
            }
            onFAILED(event);
        }


        private void onFAILED(Event event) {
            Transaction transaction = transactionRepository
                    .findById(event.transactionId()).orElse(new Transaction());
            transaction
                    .setStatus(Transaction.TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        }

    }
