package com.fintech.productservice.dto;

import com.fintech.productservice.entity.ProductType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotNull Long customerId,
        @NotNull ProductType type,
        @NotBlank @Size(max = 30) String productNumber,
        @NotNull @PositiveOrZero BigDecimal amount
) {}