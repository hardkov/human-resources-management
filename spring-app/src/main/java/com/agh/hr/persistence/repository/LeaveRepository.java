package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query("DELETE FROM Leave d WHERE d.leaveApplication.id = :id")
    void deleteByLeaveApplicationId(@Param("id") Long id);

    @Query("SELECT l FROM Leave l WHERE l.user.id IS ?1 AND (?3 = TRUE OR l.user.id IN ?2)")
    List<Leave> findByUserId(Long id, List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT l FROM Leave l WHERE l.id IS ?1 AND (?3 = TRUE OR l.user.id IN ?2)")
    Optional<Leave> findById(Long id, List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT l FROM Leave l WHERE (?2 = TRUE OR l.user.id IN ?1)")
    List<Leave> findAll(List<Long> allowedIds, boolean ignoreAuth);

}
