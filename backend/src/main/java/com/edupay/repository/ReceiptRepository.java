package com.edupay.repository;

import com.edupay.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {
    List<Receipt> findByInstitutionId(UUID institutionId);
    Optional<Receipt> findByPaymentId(UUID paymentId);
}