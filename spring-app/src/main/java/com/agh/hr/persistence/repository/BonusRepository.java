package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    List<Bonus> findByStartDateEquals(LocalDateTime startDate);
    List<Bonus> findByStartDateBefore(LocalDateTime startDate);
    List<Bonus> findByStartDateAfter(LocalDateTime startDate);

    List<Bonus> findByEndDateEquals(LocalDateTime endDate);
    List<Bonus> findByEndDateBefore(LocalDateTime endDate);
    List<Bonus> findByEndDateAfter(LocalDateTime endDate);

    List<Bonus> findByAmountBetween(BigDecimal first,BigDecimal second);
    List<Bonus> findByAmountLessThan(BigDecimal value);
    List<Bonus> findByAmountGreaterThan(BigDecimal value);


}
