package com.fintech.productservice.service;

import com.fintech.productservice.dto.ProductRequestDto;
import com.fintech.productservice.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto create(ProductRequestDto dto);

    ProductResponseDto findById(Long id);

    List<ProductResponseDto> findAll();

    List<ProductResponseDto> findByCustomerId(Long customerId);
}