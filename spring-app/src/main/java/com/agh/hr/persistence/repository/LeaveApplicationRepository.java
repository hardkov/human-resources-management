package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

    Optional<List<LeaveApplication>> findByStartDateEquals(LocalDateTime startDate);
    Optional<List<LeaveApplication>> findByStartDateBefore(LocalDateTime startDate);
    Optional<List<LeaveApplication>> findByStartDateAfter(LocalDateTime startDate);

    Optional<List<LeaveApplication>> findByEndDateEquals(LocalDateTime endDate);
    Optional<List<LeaveApplication>> findByEndDateBefore(LocalDateTime endDate);
    Optional<List<LeaveApplication>> findByEndDateAfter(LocalDateTime endDate);

    Optional<List<LeaveApplication>> findByPaidEquals(Boolean paid);

}
