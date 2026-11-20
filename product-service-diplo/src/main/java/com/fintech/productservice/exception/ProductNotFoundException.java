package com.fintech.productservice.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("No existe el producto con id " + id);
    }
}