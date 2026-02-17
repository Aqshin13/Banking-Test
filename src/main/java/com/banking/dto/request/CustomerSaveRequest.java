package com.banking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;


@Schema(
        name = "Customer Save Request",
        description = "Schema to save Customer"
)
public record CustomerSaveRequest(
        @NotBlank(message = "Ad sahesi bos ola bilmez")
        @Schema(
                description = "Customer name", example = "Sophia"
        )
        String name,

        @NotBlank(message = "Soyad hissesi bos ola bilmez")
        @Schema(
                description = "Customer surname", example = "Ismayilova"
        )
        String surname,
        @NotNull
        @Past(message = "Duzgun date daxil edin")
        @Schema(
                description = "Customer birthdate", example = "1999-02-17"
        )
        LocalDate birthDate,
        @NotBlank
        @Pattern(
                regexp = "^\\+994(50|51|55|70|77|99)\\d{7}$",
                message = "Azərbaycan mobil nömrəsi +994 ilə başlamalı və 050, 051, 055, 070, 077 və ya 099 ilə davam etməlidir (məs: +994501234567)"
        )
        @Schema(
                description = "Customer phone number", example = "+994510000000"
        )
        String gsmNumber) {
}
