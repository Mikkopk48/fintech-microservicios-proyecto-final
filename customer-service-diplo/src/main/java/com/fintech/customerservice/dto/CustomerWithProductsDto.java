package com.fintech.customerservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record CustomerWithProductsDto(
        Long id,
        String name,
        String document,
        String email,
        BigDecimal balance,
        List<ProductDto> products
) {}