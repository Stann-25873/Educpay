package com.edupay.dto.response;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class StudentResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String externalRef;
    private String level;
    private String status;
    private OffsetDateTime createdAt;
    private UUID institutionId;
    private String institutionName;
    private Set<ParentResponse> parents;

    public StudentResponse() {}

    public StudentResponse(UUID id, String firstName, String lastName, String externalRef,
                           String level, String status, OffsetDateTime createdAt,
                           UUID institutionId, String institutionName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.externalRef = externalRef;
        this.level = level;
        this.status = status;
        this.createdAt = createdAt;
        this.institutionId = institutionId;
        this.institutionName = institutionName;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public UUID getInstitutionId() { return institutionId; }
    public void setInstitutionId(UUID institutionId) { this.institutionId = institutionId; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public Set<ParentResponse> getParents() { return parents; }
    public void setParents(Set<ParentResponse> parents) { this.parents = parents; }
}
