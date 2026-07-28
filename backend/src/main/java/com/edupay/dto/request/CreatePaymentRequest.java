package com.edupay.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreatePaymentRequest {
    @NotNull(message = "Student ID is required")
    private UUID studentId;

    private UUID invoiceId;

    private UUID feeId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private String method;

    private String reference;

    private String currency = "EUR";

    private String idempotencyKey;
}
