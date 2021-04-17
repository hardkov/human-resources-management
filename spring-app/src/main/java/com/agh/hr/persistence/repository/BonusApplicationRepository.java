package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.BonusApplication;
import com.agh.hr.persistence.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface BonusApplicationRepository extends JpaRepository<BonusApplication, Long> {

    List<Contract> findByMoneyBetween(BigDecimal first, BigDecimal second);
    List<Contract> findByMoneyLessThan(BigDecimal value);
    List<Contract> findByMoneyGreaterThan(BigDecimal value);

}
