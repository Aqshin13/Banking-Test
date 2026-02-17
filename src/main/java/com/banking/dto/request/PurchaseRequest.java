package com.banking.dto.request;

import com.banking.util.annotation.CheckBalance;
import com.banking.util.annotation.IsThereCustomer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(
        name = "Purchase  Request",
        description = "Schema to purchase"
)
@CheckBalance
public record PurchaseRequest (@NotNull
                               @IsThereCustomer
                               @Schema(
                                       description = "Sender id", example = "1"
                               )
                               Long senderId,
                               @NotNull
                               @IsThereCustomer
                               @Schema(
                                       description = "Receiver id", example = "2"
                               )
                               Long receiverId,
                               @Positive(message = "Menfi pul miqdari ola bilmez")
                               @NotNull
                               @PositiveOrZero(message = "Menfi ola bilmez")
                               @Schema(
                                       description = "Amount to purchase", example = "15"
                               )
                               BigDecimal amount){
}
