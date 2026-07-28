package com.edupay.repository;
 
import com.edupay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndInstitutionId(String email, UUID institutionId);
    List<User> findByInstitutionId(UUID institutionId);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.institution.id = :tenantId")
    Optional<User> findByEmailAndTenantId(@Param("email") String email, @Param("tenantId") UUID tenantId);
}