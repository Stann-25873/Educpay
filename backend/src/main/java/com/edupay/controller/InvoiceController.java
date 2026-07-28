package com.edupay.controller;

import com.edupay.dto.response.InvoiceResponse;
import com.edupay.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable UUID id) {
        return ResponseEntity.ok(invoiceService.getInvoice(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/by-student/{studentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByStudent(@PathVariable UUID studentId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByStudent(studentId));
    }

    @GetMapping("/by-status/{status}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(invoiceService.getInvoicesByStatus(status));
    }

    @GetMapping("/by-date-range")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(invoiceService.getInvoicesByDateRange(startDate, endDate));
    }
}