package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    Optional<List<Bonus>> findByStartDateEquals(LocalDateTime startDate);
    Optional<List<Bonus>> findByStartDateBefore(LocalDateTime startDate);
    Optional<List<Bonus>> findByStartDateAfter(LocalDateTime startDate);

    Optional<List<Bonus>> findByEndDateEquals(LocalDateTime endDate);
    Optional<List<Bonus>> findByEndDateBefore(LocalDateTime endDate);
    Optional<List<Bonus>> findByEndDateAfter(LocalDateTime endDate);

    Optional<List<Bonus>> findByAmountBetween(BigDecimal first,BigDecimal second);
    Optional<List<Bonus>> findByAmountLessThan(BigDecimal value);
    Optional<List<Bonus>> findByAmountGreaterThan(BigDecimal value);


}
