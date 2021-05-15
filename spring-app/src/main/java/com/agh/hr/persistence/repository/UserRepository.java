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

    Optional<User> findUserById(Long id);

    // Legacy ......

    @Query("SELECT u FROM User u WHERE u.id IS ?1 AND u.id IN ?2")
    Optional<User> findById(Long id,List<Long> allowedIds);

    @Query("SELECT u FROM User u WHERE u.personalData.firstname IS ?1 AND u.id IN ?2")
    List<User> findByPersonalData_Firstname(String firstname,List<Long> allowedIds);

    @Query("SELECT u FROM User u WHERE u.personalData.lastname IS ?1 AND u.id IN ?2")
    List<User> findByPersonalData_Lastname(String lastname,List<Long> allowedIds);

    @Query("SELECT u FROM User u WHERE u.personalData.firstname IS ?1 AND u.personalData.lastname IS ?2 AND u.id IN ?3")
    List<User> findByPersonalData_FirstnameAndPersonalData_Lastname(String firstname,String lastname,List<Long> allowedIds);

    @Query("SELECT u FROM User u WHERE u.id IN ?1")
    List<User> findAll(List<Long> allowedIds);

    @Query("SELECT u FROM User u WHERE u.personalData.id IS ?1")
    Optional<User> getUserByPersonalDataId(Long id);

    @Query("SELECT u FROM User u WHERE u.id IS ?1")
    Optional<User> findByIdAdmin(Long id);

    @Query("SELECT u FROM User u WHERE u.personalData.firstname IS ?1")
    List<User> findByFirstnameAdmin(String firstname);

    @Query("SELECT u FROM User u WHERE u.personalData.lastname IS ?1")
    List<User> findByLastnameAdmin(String lastname);

    @Query("SELECT u FROM User u WHERE u.personalData.firstname IS ?1 AND u.personalData.lastname IS ?2")
    List<User> findByFirstnameAndLastnameAdmin(String firstname,String lastname);

    @Query("SELECT u FROM User u")
    List<User> findAllAdmin();

}
