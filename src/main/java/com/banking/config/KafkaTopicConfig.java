package com.banking.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicTopUp(){
        return TopicBuilder.name(TopicName.TOPUP_TOPIC.getName())
                .build();
    }


    @Bean
    public NewTopic topicPurchase(){
        return TopicBuilder.name(TopicName.PURCHASE.getName())
                .build();
    }


    @Bean
    public NewTopic topicRefund(){
        return TopicBuilder.name(TopicName.REFUND.getName())
                .build();
    }


}
