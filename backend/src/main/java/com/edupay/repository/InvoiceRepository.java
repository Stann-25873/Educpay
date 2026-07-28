package com.edupay.repository;

import com.edupay.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    Page<Invoice> findByInstitutionId(UUID institutionId, Pageable pageable);
    List<Invoice> findByInstitutionId(UUID institutionId);
    List<Invoice> findByStudentId(UUID studentId);
    List<Invoice> findByStatus(String status);

    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.student LEFT JOIN FETCH i.fee WHERE i.id = :id")
    Optional<Invoice> findByIdWithRelations(@Param("id") UUID id);

    @Query("SELECT i FROM Invoice i WHERE i.institution.id = :tenantId AND i.dueDate BETWEEN :startDate AND :endDate")
    List<Invoice> findByDateRange(@Param("tenantId") UUID tenantId,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);
                                   
}
