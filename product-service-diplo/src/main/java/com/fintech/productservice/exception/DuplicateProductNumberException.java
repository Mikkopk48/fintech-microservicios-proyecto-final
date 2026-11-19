package com.fintech.productservice.exception;

public class DuplicateProductNumberException extends RuntimeException {

    public DuplicateProductNumberException(String productNumber) {
        super("Ya existe un producto con el número " + productNumber);
    }
}