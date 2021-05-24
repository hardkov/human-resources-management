package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Permission;
import com.agh.hr.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByAddEquals(Boolean add);

    @Query("SELECT p FROM Permission p WHERE p.id IS ?1 AND (?3 = TRUE OR p.user.id IN ?2)")
    Optional<Permission> findById(Long id,List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT p FROM Permission p WHERE p.user.id IS ?1 AND (?3 = TRUE OR p.user.id IN ?2)")
    Optional<Permission> findByUserId(Long userId,List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT p FROM Permission p WHERE (?2 = TRUE OR p.user.id IN ?1)")
    List<Permission> findAll(List<Long> allowedIds, boolean ignoreAuth);

}
