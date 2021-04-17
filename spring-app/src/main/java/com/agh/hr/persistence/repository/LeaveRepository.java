package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    Optional<List<Leave>> findByStartDateEquals(LocalDateTime startDate);
    Optional<List<Leave>> findByStartDateBefore(LocalDateTime startDate);
    Optional<List<Leave>> findByStartDateAfter(LocalDateTime startDate);

    Optional<List<Leave>> findByEndDateEquals(LocalDateTime endDate);
    Optional<List<Leave>> findByEndDateBefore(LocalDateTime endDate);
    Optional<List<Leave>> findByEndDateAfter(LocalDateTime endDate);

    Optional<List<Leave>> findByPaidEquals(Boolean paid);

}
