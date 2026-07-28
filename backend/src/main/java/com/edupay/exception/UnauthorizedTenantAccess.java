package com.edupay.exception;

public class UnauthorizedTenantAccess extends RuntimeException {
    public UnauthorizedTenantAccess() {}
    public UnauthorizedTenantAccess(String message) { super(message); }
}
