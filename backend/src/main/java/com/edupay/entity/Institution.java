package com.edupay.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "institutions")
public class Institution {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Parent> parents = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accountant> accountants = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Fee> fees = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Receipt> receipts = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuditLog> auditLogs = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }
    public Set<Student> getStudents() { return students; }
    public void setStudents(Set<Student> students) { this.students = students; }
    public Set<Parent> getParents() { return parents; }
    public void setParents(Set<Parent> parents) { this.parents = parents; }
    public Set<Teacher> getTeachers() { return teachers; }
    public void setTeachers(Set<Teacher> teachers) { this.teachers = teachers; }
    public Set<Accountant> getAccountants() { return accountants; }
    public void setAccountants(Set<Accountant> accountants) { this.accountants = accountants; }
    public Set<Fee> getFees() { return fees; }
    public void setFees(Set<Fee> fees) { this.fees = fees; }
    public Set<Payment> getPayments() { return payments; }
    public void setPayments(Set<Payment> payments) { this.payments = payments; }
    public Set<Invoice> getInvoices() { return invoices; }
    public void setInvoices(Set<Invoice> invoices) { this.invoices = invoices; }
    public Set<Receipt> getReceipts() { return receipts; }
    public void setReceipts(Set<Receipt> receipts) { this.receipts = receipts; }
    public Set<Notification> getNotifications() { return notifications; }
    public void setNotifications(Set<Notification> notifications) { this.notifications = notifications; }
    public Set<AuditLog> getAuditLogs() { return auditLogs; }
    public void setAuditLogs(Set<AuditLog> auditLogs) { this.auditLogs = auditLogs; }
}
