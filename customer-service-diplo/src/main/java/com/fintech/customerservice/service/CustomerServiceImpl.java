package com.fintech.customerservice.service;

import com.fintech.customerservice.client.ProductClient;
import com.fintech.customerservice.dto.CustomerRequestDto;
import com.fintech.customerservice.dto.CustomerResponseDto;
import com.fintech.customerservice.dto.CustomerWithProductsDto;
import com.fintech.customerservice.dto.ProductDto;
import com.fintech.customerservice.entity.Customer;
import com.fintech.customerservice.exception.CustomerNotFoundException;
import com.fintech.customerservice.exception.DuplicateDocumentException;
import com.fintech.customerservice.exception.DuplicateEmailException;
import com.fintech.customerservice.mapper.CustomerMapper;
import com.fintech.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final ProductClient productClient;

    @Override
    @Transactional
    public CustomerResponseDto create(CustomerRequestDto dto) {
        if (repository.existsByDocument(dto.document())) {
            throw new DuplicateDocumentException(dto.document());
        }
        if (repository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException(dto.email());
        }
        Customer saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    public CustomerWithProductsDto findByIdWithProducts(Long id) {
        Customer customer = findCustomer(id);
        List<ProductDto> products = productClient.getProductsByCustomerId(id);
        return mapper.toDtoWithProducts(customer, products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CustomerResponseDto update(Long id, CustomerRequestDto dto) {
        Customer customer = findCustomer(id);

        boolean documentChanged = !customer.getDocument().equals(dto.document());
        if (documentChanged && repository.existsByDocument(dto.document())) {
            throw new DuplicateDocumentException(dto.document());
        }

        boolean emailChanged = !customer.getEmail().equals(dto.email());
        if (emailChanged && repository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException(dto.email());
        }

        customer.setName(dto.name());
        customer.setDocument(dto.document());
        customer.setEmail(dto.email());
        customer.setBalance(dto.balance());

        return mapper.toDto(repository.save(customer));
    }

    private Customer findCustomer(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}