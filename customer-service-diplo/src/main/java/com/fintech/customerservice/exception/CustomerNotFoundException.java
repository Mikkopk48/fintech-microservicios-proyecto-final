package com.fintech.customerservice.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("No existe el cliente con id " + id);
    }
}