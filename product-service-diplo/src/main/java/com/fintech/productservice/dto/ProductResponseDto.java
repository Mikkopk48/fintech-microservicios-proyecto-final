package com.fintech.productservice.dto;

import com.fintech.productservice.entity.ProductType;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        Long customerId,
        ProductType type,
        String productNumber,
        BigDecimal amount,
        Boolean active
) {}