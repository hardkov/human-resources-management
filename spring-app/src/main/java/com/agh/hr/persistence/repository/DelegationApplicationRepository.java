package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.DelegationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DelegationApplicationRepository extends JpaRepository<DelegationApplication, Long> {

    @Query("SELECT d FROM DelegationApplication d WHERE d.user.id = :userId")
    List<DelegationApplication> findByUserId(@Param("userId") Long userId);

    List<DelegationApplication> findByStartDateEquals(LocalDateTime startDate);
    List<DelegationApplication> findByStartDateBefore(LocalDateTime startDate);
    List<DelegationApplication> findByStartDateAfter(LocalDateTime startDate);

    List<DelegationApplication> findByEndDateEquals(LocalDateTime endDate);
    List<DelegationApplication> findByEndDateBefore(LocalDateTime endDate);
    List<DelegationApplication> findByEndDateAfter(LocalDateTime endDate);

    List<DelegationApplication> findByDestinationContaining(String destination);

}
