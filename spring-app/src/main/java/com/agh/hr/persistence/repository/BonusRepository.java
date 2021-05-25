package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    @Query("DELETE FROM Bonus d WHERE d.bonusApplication.id = :id")
    void deleteByBonusApplicationId(@Param("id") Long id);

}
