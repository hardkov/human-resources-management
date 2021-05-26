package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.BonusApplication;
import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface BonusApplicationRepository extends JpaRepository<BonusApplication, Long> {

    @Query("SELECT d FROM BonusApplication d WHERE d.user.id = :userId")
    List<BonusApplication> findByUserId(@Param("userId") Long userId);

}
