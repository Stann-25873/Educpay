package com.edupay.controller;

import com.edupay.dto.request.CreatePaymentRequest;
import com.edupay.dto.response.PaymentResponse;
import com.edupay.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/by-student/{studentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStudent(@PathVariable UUID studentId) {
        return ResponseEntity.ok(paymentService.getPaymentsByStudent(studentId));
    }

    @GetMapping("/by-method/{method}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByMethod(@PathVariable String method) {
        return ResponseEntity.ok(paymentService.getPaymentsByMethod(method));
    }

    @GetMapping("/by-date-range")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(paymentService.getPaymentsByDateRange(startDate, endDate));
    }
}