package com.fintech.productservice.controller;

import com.fintech.productservice.dto.ProductRequestDto;
import com.fintech.productservice.dto.ProductResponseDto;
import com.fintech.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/customer/{customerId}")
    public List<ProductResponseDto> findByCustomerId(@PathVariable Long customerId) {
        return service.findByCustomerId(customerId);
    }
}