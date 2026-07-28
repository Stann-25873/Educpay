package com.edupay.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public class InstitutionResponse {

    private UUID id;
    private String name;
    private String type;
    private OffsetDateTime createdAt;

    public InstitutionResponse() {}

    public InstitutionResponse(UUID id, String name, String type, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
