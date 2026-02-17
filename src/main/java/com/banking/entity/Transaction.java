package com.banking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(nullable = false)
    private BigDecimal amount;
    private Long sender;
    private Long receiver;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @OneToOne
    private Transaction purchase;
    private boolean isRefund;

    public enum TransactionType {
        TOP_UP, PURCHASE, REFUND
    }


    public enum TransactionStatus {
        FAILED, SUCCESS, PROCESSING
    }

}
