package com.edupay.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean isActive;
    private OffsetDateTime lastLoginAt;
    private OffsetDateTime createdAt;
    private UUID institutionId;
    private String institutionName;
    private UUID roleId;
    private String roleCode;
    private String roleName;

    public UserResponse() {}

    public UserResponse(UUID id, String email, String firstName, String lastName, String phone,
                        Boolean isActive, OffsetDateTime lastLoginAt, OffsetDateTime createdAt,
                        UUID institutionId, String institutionName, UUID roleId,
                        String roleCode, String roleName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.isActive = isActive;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.institutionId = institutionId;
        this.institutionName = institutionName;
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public OffsetDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(OffsetDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public UUID getInstitutionId() { return institutionId; }
    public void setInstitutionId(UUID institutionId) { this.institutionId = institutionId; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
