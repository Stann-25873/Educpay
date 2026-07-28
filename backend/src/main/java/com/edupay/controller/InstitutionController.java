package com.edupay.controller;

import com.edupay.dto.request.CreateInstitutionRequest;
import com.edupay.dto.response.InstitutionResponse;
import com.edupay.service.InstitutionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<InstitutionResponse> createInstitution(@Valid @RequestBody CreateInstitutionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(institutionService.createInstitution(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstitutionResponse> getInstitution(@PathVariable UUID id) {
        return ResponseEntity.ok(institutionService.getInstitution(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstitutionResponse> updateInstitution(@PathVariable UUID id,
                                                                  @Valid @RequestBody CreateInstitutionRequest request) {
        return ResponseEntity.ok(institutionService.updateInstitution(id, request));
    }
}