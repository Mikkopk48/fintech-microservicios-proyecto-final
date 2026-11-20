package com.fintech.customerservice.exception;

public class DuplicateDocumentException extends RuntimeException {

    public DuplicateDocumentException(String document) {
        super("Ya existe un cliente con el documento " + document);
    }
}