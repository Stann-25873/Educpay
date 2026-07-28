package com.edupay.repository;

import com.edupay.entity.Fee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeeRepository extends JpaRepository<Fee, UUID> {
    Page<Fee> findByInstitutionId(UUID institutionId, Pageable pageable);
    List<Fee> findByInstitutionId(UUID institutionId);
    List<Fee> findByLevel(String level);
}
