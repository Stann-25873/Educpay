package com.edupay.exception;

public class DuplicatePaymentException extends RuntimeException {
    public DuplicatePaymentException() {}
    public DuplicatePaymentException(String message) { super(message); }
}
