package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Delegation;
import com.agh.hr.persistence.model.DelegationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {

    List<Delegation> findByStartDateEquals(LocalDateTime startDate);
    List<Delegation> findByStartDateBefore(LocalDateTime startDate);
    List<Delegation> findByStartDateAfter(LocalDateTime startDate);

    List<Delegation> findByEndDateEquals(LocalDateTime endDate);
    List<Delegation> findByEndDateBefore(LocalDateTime endDate);
    List<Delegation> findByEndDateAfter(LocalDateTime endDate);

    List<DelegationApplication> findByDestinationContaining(String destination);


}
