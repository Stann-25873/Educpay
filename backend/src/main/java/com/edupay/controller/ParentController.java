package com.edupay.controller;

import com.edupay.dto.request.CreateParentRequest;
import com.edupay.dto.response.ParentResponse;
import com.edupay.service.ParentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<ParentResponse> createParent(@Valid @RequestBody CreateParentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parentService.createParent(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ParentResponse> getParent(@PathVariable UUID id) {
        return ResponseEntity.ok(parentService.getParent(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ParentResponse>> getAllParents() {
        return ResponseEntity.ok(parentService.getAllParents());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<ParentResponse> updateParent(@PathVariable UUID id,
                                                        @Valid @RequestBody CreateParentRequest request) {
        return ResponseEntity.ok(parentService.updateParent(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteParent(@PathVariable UUID id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}