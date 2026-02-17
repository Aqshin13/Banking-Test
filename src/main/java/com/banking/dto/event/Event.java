package com.banking.dto.event;

import java.math.BigDecimal;

public record Event(Long transactionId,
                    Long senderId,
                    Long receiverId,
                    BigDecimal amount) {
}
