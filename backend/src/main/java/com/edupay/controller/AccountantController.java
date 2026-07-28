package com.edupay.controller;

import com.edupay.dto.request.CreateAccountantRequest;
import com.edupay.dto.response.AccountantResponse;
import com.edupay.service.AccountantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accountants")
public class AccountantController {

    private final AccountantService accountantService;

    public AccountantController(AccountantService accountantService) {
        this.accountantService = accountantService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountantResponse> createAccountant(@Valid @RequestBody CreateAccountantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountantService.createAccountant(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountantResponse> getAccountant(@PathVariable UUID id) {
        return ResponseEntity.ok(accountantService.getAccountant(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AccountantResponse>> getAllAccountants() {
        return ResponseEntity.ok(accountantService.getAllAccountants());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountantResponse> updateAccountant(@PathVariable UUID id,
                                                                @Valid @RequestBody CreateAccountantRequest request) {
        return ResponseEntity.ok(accountantService.updateAccountant(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccountant(@PathVariable UUID id) {
        accountantService.deleteAccountant(id);
        return ResponseEntity.noContent().build();
    }
}