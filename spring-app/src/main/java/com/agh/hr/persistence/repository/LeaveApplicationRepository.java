package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

    List<LeaveApplication> findByStartDateEquals(LocalDateTime startDate);
    List<LeaveApplication> findByStartDateBefore(LocalDateTime startDate);
    List<LeaveApplication> findByStartDateAfter(LocalDateTime startDate);

    List<LeaveApplication> findByEndDateEquals(LocalDateTime endDate);
    List<LeaveApplication> findByEndDateBefore(LocalDateTime endDate);
    List<LeaveApplication> findByEndDateAfter(LocalDateTime endDate);

    List<LeaveApplication> findByPaidEquals(Boolean paid);

}
