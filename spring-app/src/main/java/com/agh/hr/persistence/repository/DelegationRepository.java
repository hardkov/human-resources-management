package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Delegation;
import com.agh.hr.persistence.model.DelegationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {

    Optional<List<Delegation>> findByStartDateEquals(LocalDateTime startDate);
    Optional<List<Delegation>> findByStartDateBefore(LocalDateTime startDate);
    Optional<List<Delegation>> findByStartDateAfter(LocalDateTime startDate);

    Optional<List<Delegation>> findByEndDateEquals(LocalDateTime endDate);
    Optional<List<Delegation>> findByEndDateBefore(LocalDateTime endDate);
    Optional<List<Delegation>> findByEndDateAfter(LocalDateTime endDate);

    Optional<List<DelegationApplication>> findByDestinationContaining(String destination);


}
