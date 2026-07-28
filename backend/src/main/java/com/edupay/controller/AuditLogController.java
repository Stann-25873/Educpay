package com.edupay.controller;

import com.edupay.dto.response.AuditLogResponse;
import com.edupay.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogs() {
        return ResponseEntity.ok(auditLogService.getAuditLogs());
    }

    @GetMapping("/by-date-range")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByDateRange(
            @RequestParam OffsetDateTime startDate,
            @RequestParam OffsetDateTime endDate) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByDateRange(startDate, endDate));
    }
}