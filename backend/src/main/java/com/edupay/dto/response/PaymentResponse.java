package com.edupay.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


public class PaymentResponse {
    private UUID id;
    private UUID studentId;
    private String studentName;
    private UUID invoiceId;
    private String invoiceNumber;
    private UUID feeId;
    private String feeTitle;
    private BigDecimal amount;
    private String currency;
    private String method;
    private String reference;
    private OffsetDateTime paidAt;
    private OffsetDateTime createdAt;
    private UUID institutionId;
    private String institutionName;

    public PaymentResponse() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public UUID getInvoiceId() { return invoiceId; }
    public void setInvoiceId(UUID invoiceId) { this.invoiceId = invoiceId; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public UUID getFeeId() { return feeId; }
    public void setFeeId(UUID feeId) { this.feeId = feeId; }
    public String getFeeTitle() { return feeTitle; }
    public void setFeeTitle(String feeTitle) { this.feeTitle = feeTitle; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public OffsetDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(OffsetDateTime paidAt) { this.paidAt = paidAt; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public UUID getInstitutionId() { return institutionId; }
    public void setInstitutionId(UUID institutionId) { this.institutionId = institutionId; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
}
