package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.BonusApplication;
import com.agh.hr.persistence.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BonusApplicationRepository extends JpaRepository<BonusApplication, Long> {

    Optional<List<Contract>> findByMoneyBetween(BigDecimal first, BigDecimal second);
    Optional<List<Contract>> findByMoneyLessThan(BigDecimal value);
    Optional<List<Contract>> findByMoneyGreaterThan(BigDecimal value);

}
