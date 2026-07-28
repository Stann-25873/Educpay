package com.edupay.repository;

import com.edupay.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Page<Student> findByInstitutionId(UUID institutionId, Pageable pageable);
    List<Student> findByInstitutionId(UUID institutionId);
    List<Student> findByLevel(String level);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.parents WHERE s.id = :id")
    Optional<Student> findByIdWithParents(@Param("id") UUID id);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.institution WHERE s.id = :id")
    Optional<Student> findByIdWithInstitution(@Param("id") UUID id);
}
