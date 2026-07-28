package com.edupay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public class CreateStudentRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @Size(max = 100, message = "External ref must not exceed 100 characters")
    private String externalRef;

    @Size(max = 100, message = "Level must not exceed 100 characters")
    private String level;

    private Set<UUID> parentIds;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Set<UUID> getParentIds() { return parentIds; }
    public void setParentIds(Set<UUID> parentIds) { this.parentIds = parentIds; }
}
