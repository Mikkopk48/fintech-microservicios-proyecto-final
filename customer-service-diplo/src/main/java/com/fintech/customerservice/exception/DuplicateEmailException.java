package com.fintech.customerservice.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Ya existe un cliente con el correo " + email);
    }
}