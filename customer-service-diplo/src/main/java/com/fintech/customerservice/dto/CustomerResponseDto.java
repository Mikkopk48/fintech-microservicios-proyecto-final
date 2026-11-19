package com.fintech.customerservice.dto;

import java.math.BigDecimal;

public record CustomerResponseDto(
        Long id,
        String name,
        String document,
        String email,
        BigDecimal balance
) {}