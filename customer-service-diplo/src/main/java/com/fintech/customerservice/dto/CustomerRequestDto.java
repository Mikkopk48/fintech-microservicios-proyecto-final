package com.fintech.customerservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CustomerRequestDto(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 20) String document,
        @NotBlank @Email @Size(max = 150) String email,
        @NotNull @PositiveOrZero @Digits(integer = 13, fraction = 2) BigDecimal balance) {
}