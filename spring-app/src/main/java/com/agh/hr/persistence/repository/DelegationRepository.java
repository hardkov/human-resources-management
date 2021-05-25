package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Delegation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {

    @Query("DELETE FROM Delegation d WHERE d.delegationApplication.id = :id")
    void deleteByDelegationApplicationId(@Param("id") Long id);

}
