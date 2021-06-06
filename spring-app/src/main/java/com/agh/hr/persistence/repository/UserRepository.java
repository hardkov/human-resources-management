package com.agh.hr.persistence.repository;

import com.agh.hr.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.supervisor.id = :supervisorId")
    List<User> findSubordinates(@Param("supervisorId") Long supervisorId);

    @Query("SELECT u FROM User u WHERE u.id IN ?1 AND (?3 = TRUE OR u.id IN ?2)")
    List<User> findUsersWithIds(List<Long> userIds,List<Long> allowedIds, boolean ignoreAuth);

    Optional<User> findUserById(Long id);

    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.id IN ?2")
    Optional<User> findById(Long id,List<Long> allowedIds);

    @Query("SELECT u FROM User u WHERE u.id = ?1 AND (?3 = TRUE OR u.id IN ?2)")
    Optional<User> findById(Long id,List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT u FROM User u WHERE u.personalData.firstname = ?1 AND (?3 = TRUE OR u.id IN ?2)")
    List<User> findByPersonalData_Firstname(String firstname,List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT u FROM User u WHERE u.personalData.lastname = ?1 AND (?3 = TRUE OR u.id IN ?2)")
    List<User> findByPersonalData_Lastname(String lastname,List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT u FROM User u WHERE u.personalData.firstname = ?1 AND u.personalData.lastname IS ?2 AND " +
            "(?4 = TRUE OR u.id IN ?3)")
    List<User> findByPersonalData_FirstnameAndPersonalData_Lastname(String firstname,String lastname,
                                                                    List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT u FROM User u WHERE (?2 = TRUE OR u.id IN ?1)")
    List<User> findAll(List<Long> allowedIds, boolean ignoreAuth);

    @Query("SELECT u FROM User u WHERE u.personalData.id IS ?1")
    Optional<User> getUserByPersonalDataId(Long id);

}
