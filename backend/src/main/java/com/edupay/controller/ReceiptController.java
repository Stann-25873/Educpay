package com.edupay.controller;

import com.edupay.dto.response.ReceiptResponse;
import com.edupay.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReceiptResponse> getReceipt(@PathVariable UUID id) {
        return ResponseEntity.ok(receiptService.getReceipt(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReceiptResponse>> getAllReceipts() {
        return ResponseEntity.ok(receiptService.getAllReceipts());
    }

    @GetMapping("/by-payment/{paymentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReceiptResponse> getReceiptByPayment(@PathVariable UUID paymentId) {
        return ResponseEntity.ok(receiptService.getReceiptByPayment(paymentId));
    }
}