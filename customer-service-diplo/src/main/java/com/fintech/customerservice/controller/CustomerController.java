package com.fintech.customerservice.controller;

import com.fintech.customerservice.dto.CustomerRequestDto;
import com.fintech.customerservice.dto.CustomerResponseDto;
import com.fintech.customerservice.dto.CustomerWithProductsDto;
import com.fintech.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@Valid @RequestBody CustomerRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public CustomerWithProductsDto findById(@PathVariable Long id) {
        return service.findByIdWithProducts(id);
    }

    @GetMapping
    public List<CustomerResponseDto> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@PathVariable Long id,
                                      @Valid @RequestBody CustomerRequestDto dto) {
        return service.update(id, dto);
    }
}