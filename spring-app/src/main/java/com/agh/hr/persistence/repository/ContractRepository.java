package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.id IS ?1 AND (?3 = TRUE OR c.user.id IN ?2)")
    Optional<Contract> findById(Long id, List<Long> allowedList, boolean ignoreAuth);

    @Query("SELECT c FROM Contract c WHERE c.user.id IS ?1 AND (?3 = TRUE OR c.user.id IN ?2)")
    List<Contract> findByUserId(Long id,List<Long> allowedList, boolean ignoreAuth);

    @Query("SELECT c FROM Contract c WHERE (?2 = TRUE OR c.user.id IN ?1)")
    List<Contract> findAll(List<Long> allowedList, boolean ignoreAuth);

    List<Contract> findByStartDateEquals(LocalDate startDate);
    List<Contract> findByStartDateBefore(LocalDate startDate);
    List<Contract> findByStartDateAfter(LocalDate startDate);

    List<Contract> findByEndDateEquals(LocalDate endDate);
    List<Contract> findByEndDateBefore(LocalDate endDate);
    List<Contract> findByEndDateAfter(LocalDate endDate);

    List<Contract> findByContractType(ContractType contractType);

    List<Contract> findByBaseSalaryBetween(BigDecimal first,BigDecimal second);
    List<Contract> findByBaseSalaryLessThan(BigDecimal value);
    List<Contract> findByBaseSalaryGreaterThan(BigDecimal value);


}
