package com.edupay.dto.response;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class ParentResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private OffsetDateTime createdAt;
    private UUID institutionId;
    private String institutionName;
    private Set<StudentResponse> students;

    public ParentResponse() {}

    public ParentResponse(UUID id, String firstName, String lastName, String email,
                          String phone, OffsetDateTime createdAt,
                          UUID institutionId, String institutionName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
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
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public UUID getInstitutionId() { return institutionId; }
    public void setInstitutionId(UUID institutionId) { this.institutionId = institutionId; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public Set<StudentResponse> getStudents() { return students; }
    public void setStudents(Set<StudentResponse> students) { this.students = students; }
}
