package com.banking.config;

public enum TopicName {

    TOPUP_TOPIC("created-topup-topic"),
    PURCHASE("created-purchase-topic"),
    REFUND("created-refund-topic");

    private final String name;


    TopicName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
