package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query("SELECT l FROM Leave l WHERE l.id IS ?1 AND l.user.id IN ?2")
    Optional<Leave> findById(Long id, List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.user.id IN ?1")
    List<Leave> findAll(List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.startDate IS ?1 AND l.user.id IN ?2")
    List<Leave> findByStartDateEquals(LocalDateTime startDate, List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.startDate< ?1 AND l.user.id IN ?2")
    List<Leave> findByStartDateBefore(LocalDateTime startDate, List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.startDate> ?1 AND l.user.id IN ?2")
    List<Leave> findByStartDateAfter(LocalDateTime startDate, List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.endDate IS ?1 AND l.user.id IN ?2")
    List<Leave> findByEndDateEquals(LocalDateTime endDate, List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.endDate < ?1 AND l.user.id IN ?2")
    List<Leave> findByEndDateBefore(LocalDateTime endDate, List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.endDate > ?1 AND l.user.id IN ?2")
    List<Leave> findByEndDateAfter(LocalDateTime endDate, List<Long> allowedIds);

    @Query("SELECT l FROM Leave l WHERE l.paid IS ?1 AND l.user.id IN ?2")
    List<Leave> findByPaidEquals(Boolean paid, List<Long> allowedIds);

}
