package com.edupay.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class FeeResponse {
    private UUID id;
    private String code;
    private String title;
    private String description;
    private BigDecimal amount;
    private String currency;
    private String billingPeriod;
    private String level;
    private OffsetDateTime createdAt;
    private UUID institutionId;
    private String institutionName;

    public FeeResponse() {}

    public FeeResponse(UUID id, String code, String title, String description,
                       BigDecimal amount, String currency, String billingPeriod,
                       String level, OffsetDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.billingPeriod = billingPeriod;
        this.level = level;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getBillingPeriod() { return billingPeriod; }
    public void setBillingPeriod(String billingPeriod) { this.billingPeriod = billingPeriod; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public UUID getInstitutionId() { return institutionId; }
    public void setInstitutionId(UUID institutionId) { this.institutionId = institutionId; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
}
