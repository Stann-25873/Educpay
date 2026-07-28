package com.edupay.controller;

import com.edupay.dto.request.CreateFeeRequest;
import com.edupay.dto.response.FeeResponse;
import com.edupay.service.FeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<FeeResponse> createFee(@Valid @RequestBody CreateFeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFee(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FeeResponse> getFee(@PathVariable UUID id) {
        return ResponseEntity.ok(feeService.getFee(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FeeResponse>> getAllFees() {
        return ResponseEntity.ok(feeService.getAllFees());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<FeeResponse> updateFee(@PathVariable UUID id,
                                                  @Valid @RequestBody CreateFeeRequest request) {
        return ResponseEntity.ok(feeService.updateFee(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteFee(@PathVariable UUID id) {
        feeService.deleteFee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-level/{level}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FeeResponse>> getFeesByLevel(@PathVariable String level) {
        return ResponseEntity.ok(feeService.getFeesByLevel(level));
    }
}