package com.fintech.productservice.service;

import com.fintech.productservice.dto.ProductRequestDto;
import com.fintech.productservice.dto.ProductResponseDto;
import com.fintech.productservice.entity.Product;
import com.fintech.productservice.exception.DuplicateProductNumberException;
import com.fintech.productservice.exception.ProductNotFoundException;
import com.fintech.productservice.mapper.ProductMapper;
import com.fintech.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    @Transactional
    public ProductResponseDto create(ProductRequestDto dto) {
        if (repository.existsByProductNumber(dto.productNumber())) {
            throw new DuplicateProductNumberException(dto.productNumber());
        }
        Product saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(mapper::toDto)
                .toList();
    }
}