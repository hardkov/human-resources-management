package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByStartDateEquals(LocalDateTime startDate);
    List<Contract> findByStartDateBefore(LocalDateTime startDate);
    List<Contract> findByStartDateAfter(LocalDateTime startDate);

    List<Contract> findByEndDateEquals(LocalDateTime endDate);
    List<Contract> findByEndDateBefore(LocalDateTime endDate);
    List<Contract> findByEndDateAfter(LocalDateTime endDate);

    List<Contract> findByContractType(ContractType contractType);

    List<Contract> findByBaseSalaryBetween(BigDecimal first,BigDecimal second);
    List<Contract> findByBaseSalaryLessThan(BigDecimal value);
    List<Contract> findByBaseSalaryGreaterThan(BigDecimal value);


}
