package com.edupay.repository;
import com.edupay.entity.Parent;
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
public interface ParentRepository extends JpaRepository<Parent, UUID> {
    Page<Parent> findByInstitutionId(UUID institutionId, Pageable pageable);
    List<Parent> findByInstitutionId(UUID institutionId);

    @Query("SELECT p FROM Parent p LEFT JOIN FETCH p.students WHERE p.id = :id")
    Optional<Parent> findByIdWithStudents(@Param("id") UUID id);
}
