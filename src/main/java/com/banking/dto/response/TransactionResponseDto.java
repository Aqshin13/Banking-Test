package com.banking.dto.response;

import com.banking.entity.Transaction;

import java.math.BigDecimal;

public record TransactionResponseDto(
        Long transactionId, Long sender, Long receiver, Transaction.TransactionType type, BigDecimal amount
        , Transaction.TransactionStatus status, boolean isRefund
) {
}
