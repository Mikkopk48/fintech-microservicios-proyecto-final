package com.fintech.customerservice.service;

import com.fintech.customerservice.dto.CustomerRequestDto;
import com.fintech.customerservice.dto.CustomerResponseDto;
import com.fintech.customerservice.dto.CustomerWithProductsDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto create(CustomerRequestDto dto);

    CustomerWithProductsDto findByIdWithProducts(Long id);

    List<CustomerResponseDto> findAll();

    CustomerResponseDto update(Long id, CustomerRequestDto dto);
}