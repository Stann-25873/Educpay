package com.edupay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateFeeRequest {
    @NotBlank(message = "Fee code is required")
    private String code;

    @NotBlank(message = "Fee title is required")
    private String title;

    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency = "EUR";

    @NotBlank(message = "Billing period is required")
    private String billingPeriod;

    private String level;
}
