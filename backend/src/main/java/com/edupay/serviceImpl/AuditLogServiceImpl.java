package com.edupay.serviceImpl;

import com.edupay.dto.response.AuditLogResponse;
import com.edupay.entity.AuditLog;
import com.edupay.entity.Institution;
import com.edupay.entity.User;
import com.edupay.exception.ResourceNotFound;
import com.edupay.mapper.AuditLogMapper;
import com.edupay.repository.AuditLogRepository;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.UserRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.AuditLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private final AuditLogMapper auditLogMapper;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository,
                                InstitutionRepository institutionRepository,
                                UserRepository userRepository,
                                AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.institutionRepository = institutionRepository;
        this.userRepository = userRepository;
        this.auditLogMapper = auditLogMapper;
    }

    @Override
    @Transactional
    public void log(String action, String entityType, UUID entityId, String details, String ipAddress) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        AuditLog auditLog = new AuditLog();
        auditLog.setInstitution(institution);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setDetails(details);
        auditLog.setIpAddress(ipAddress);

        SecurityUtils.getCurrentUsername().ifPresent(username -> {
            userRepository.findByEmail(username).ifPresent(auditLog::setActor);
        });

        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> getAuditLogs() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return auditLogRepository.findByInstitutionIdOrderByCreatedAtDesc(tenantId).stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> getAuditLogsByDateRange(OffsetDateTime startDate, OffsetDateTime endDate) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return auditLogRepository.findByInstitutionIdOrderByCreatedAtDesc(tenantId).stream()
                .filter(log -> !log.getCreatedAt().isBefore(startDate) && !log.getCreatedAt().isAfter(endDate))
                .map(auditLogMapper::toResponse)
                .toList();
    }
}