package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<List<Contract>> findByStartDateEquals(LocalDateTime startDate);
    Optional<List<Contract>> findByStartDateBefore(LocalDateTime startDate);
    Optional<List<Contract>> findByStartDateAfter(LocalDateTime startDate);

    Optional<List<Contract>> findByEndDateEquals(LocalDateTime endDate);
    Optional<List<Contract>> findByEndDateBefore(LocalDateTime endDate);
    Optional<List<Contract>> findByEndDateAfter(LocalDateTime endDate);

    Optional<List<Contract>> findByContractType(ContractType contractType);

    Optional<List<Contract>> findByBaseSaleryBetween(BigDecimal first,BigDecimal second);
    Optional<List<Contract>> findByBaseSaleryLessThan(BigDecimal value);
    Optional<List<Contract>> findByBaseSaleryGreaterThan(BigDecimal value);


}
