package com.edupay.repository;

import com.edupay.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Page<Payment> findByInstitutionId(UUID institutionId, Pageable pageable);
    List<Payment> findByInstitutionId(UUID institutionId);
    List<Payment> findByStudentId(UUID studentId);
    List<Payment> findByInvoiceId(UUID invoiceId);
    Optional<Payment> findByReference(String reference);

    @Query("SELECT p FROM Payment p LEFT JOIN FETCH p.student LEFT JOIN FETCH p.invoice WHERE p.id = :id")
    Optional<Payment> findByIdWithRelations(@Param("id") UUID id);

    @Query("SELECT p FROM Payment p WHERE p.institution.id = :tenantId AND p.paidAt BETWEEN :startDate AND :endDate")
    List<Payment> findByDateRange(@Param("tenantId") UUID tenantId,
                                  @Param("startDate") OffsetDateTime startDate,
                                  @Param("endDate") OffsetDateTime endDate);
}