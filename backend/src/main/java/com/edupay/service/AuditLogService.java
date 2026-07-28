package com.edupay.service;

import com.edupay.dto.response.AuditLogResponse;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface AuditLogService {
    void log(String action, String entityType, UUID entityId, String details, String ipAddress);
    List<AuditLogResponse> getAuditLogs();
    List<AuditLogResponse> getAuditLogsByDateRange(OffsetDateTime startDate, OffsetDateTime endDate);
}
