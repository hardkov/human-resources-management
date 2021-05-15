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

    @Query("SELECT p FROM Permission p WHERE p.id IS ?1 AND p.user.id IN ?2")
    Optional<Permission> findById(Long id,List<Long> allowedIds);

    @Query("SELECT p FROM Permission p WHERE p.user.id IS ?1 AND p.user.id IN ?2")
    Optional<Permission> findByUserId(Long userId,List<Long> allowedIds);

    @Query("SELECT p FROM Permission p WHERE p.user.id IN ?1")
    List<Permission> findAll(List<Long> allowedIds);

    @Query("SELECT p FROM Permission p WHERE p.id IS ?1")
    Optional<Permission> findByIdAdmin(Long id);

    @Query("SELECT p FROM Permission p WHERE p.user.id IS ?1")
    Optional<Permission> findByUserIdAdmin(Long userId);

    @Query("SELECT p FROM Permission p")
    List<Permission> findAllAdmin();
}
