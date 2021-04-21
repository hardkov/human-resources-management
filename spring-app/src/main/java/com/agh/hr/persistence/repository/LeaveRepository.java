package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByStartDateEquals(LocalDateTime startDate);
    List<Leave> findByStartDateBefore(LocalDateTime startDate);
    List<Leave> findByStartDateAfter(LocalDateTime startDate);

    List<Leave> findByEndDateEquals(LocalDateTime endDate);
    List<Leave> findByEndDateBefore(LocalDateTime endDate);
    List<Leave> findByEndDateAfter(LocalDateTime endDate);

    List<Leave> findByPaidEquals(Boolean paid);

}
