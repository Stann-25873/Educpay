package com.edupay.mapper;

import com.edupay.dto.response.AuditLogResponse;
import com.edupay.entity.AuditLog;
import com.edupay.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogResponse toResponse(AuditLog auditLog) {
        AuditLogResponse response = new AuditLogResponse();
        response.setId(auditLog.getId());
        response.setAction(auditLog.getAction());
        response.setEntity(auditLog.getEntityType());
        response.setEntityId(auditLog.getEntityId());
        response.setDescription(auditLog.getDetails());
        response.setIpAddress(auditLog.getIpAddress());
        response.setCreatedAt(auditLog.getCreatedAt());

        if (auditLog.getInstitution() != null) {
            response.setInstitutionId(auditLog.getInstitution().getId());
        }

        User actor = auditLog.getActor();
        if (actor != null) {
            response.setActorId(actor.getId());
            response.setActorEmail(actor.getEmail());
            response.setActorName(actor.getFirstName() + " " + actor.getLastName());
        }

        return response;
    }
}