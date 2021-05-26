package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.DelegationApplication;
import com.agh.hr.persistence.model.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

    @Query("SELECT d FROM LeaveApplication d WHERE d.user.id = :userId")
    List<LeaveApplication> findByUserId(@Param("userId") Long userId);

    List<LeaveApplication> findByStartDateEquals(LocalDateTime startDate);
    List<LeaveApplication> findByStartDateBefore(LocalDateTime startDate);
    List<LeaveApplication> findByStartDateAfter(LocalDateTime startDate);

    List<LeaveApplication> findByEndDateEquals(LocalDateTime endDate);
    List<LeaveApplication> findByEndDateBefore(LocalDateTime endDate);
    List<LeaveApplication> findByEndDateAfter(LocalDateTime endDate);

    List<LeaveApplication> findByPaidEquals(Boolean paid);

}
