package com.fintech.customerservice.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        Long customerId,
        String type,
        String productNumber,
        BigDecimal amount,
        Boolean active
) {}