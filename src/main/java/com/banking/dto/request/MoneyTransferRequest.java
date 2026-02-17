package com.banking.dto.request;

import com.banking.util.annotation.IsThereCustomer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MoneyTransferRequest(@NotNull
                                   @IsThereCustomer
                                   Long senderId,
                                   @NotNull
                                   @IsThereCustomer
                                   Long receiverId,
                                   @Positive(message = "Menfi deyer ola bilmez")
                                   @NotNull
                                   BigDecimal amount) {
}
