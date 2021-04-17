package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.DelegationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DelegationApplicationRepository extends JpaRepository<DelegationApplication, Long> {

    Optional<List<DelegationApplication>> findByStartDateEquals(LocalDateTime startDate);
    Optional<List<DelegationApplication>> findByStartDateBefore(LocalDateTime startDate);
    Optional<List<DelegationApplication>> findByStartDateAfter(LocalDateTime startDate);

    Optional<List<DelegationApplication>> findByEndDateEquals(LocalDateTime endDate);
    Optional<List<DelegationApplication>> findByEndDateBefore(LocalDateTime endDate);
    Optional<List<DelegationApplication>> findByEndDateAfter(LocalDateTime endDate);

    Optional<List<DelegationApplication>> findByDestinationContaining(String destination);

}
