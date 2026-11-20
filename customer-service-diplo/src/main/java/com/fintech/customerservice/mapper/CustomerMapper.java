package com.fintech.customerservice.mapper;

import com.fintech.customerservice.dto.*;
import com.fintech.customerservice.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setDocument(dto.document());
        customer.setEmail(dto.email());
        customer.setBalance(dto.balance());
        return customer;
    }

    public CustomerResponseDto toDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getDocument(),
                customer.getEmail(),
                customer.getBalance()
        );
    }

    public CustomerWithProductsDto toDtoWithProducts(Customer customer, List<ProductDto> products) {
        return new CustomerWithProductsDto(
                customer.getId(),
                customer.getName(),
                customer.getDocument(),
                customer.getEmail(),
                customer.getBalance(),
                products
        );
    }
}