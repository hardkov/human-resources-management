package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    @Query("SELECT d FROM PersonalData d WHERE d.user.id IN ?1")
    List<PersonalData> findAll(List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.id IS ?1 AND d.user.id IN ?2")
    Optional<PersonalData> findById(Long id, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.firstname IS ?1 AND d.user.id IN ?2")
    List<PersonalData> findByFirstname(String firstname, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.lastname IS ?1 AND d.user.id IN ?2")
    List<PersonalData> findByLastname(String lastname, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.firstname IS ?1 AND d.lastname IS ?2 AND d.user.id IN ?3")
    List<PersonalData> findByFirstnameAndLastname(String firstname,String lastname, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.address IS ?1 AND d.user.id IN ?2")
    List<PersonalData> findByAddress(String address, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.phoneNumber IS ?1 AND d.user.id IN ?2")
    List<PersonalData> findByPhoneNumber(String phoneNumber, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.email IS ?1 AND d.user.id IN ?2")
    List<PersonalData> findByEmail(String email, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.birthdate IS ?1 AND d.user.id IN ?2")
    List<PersonalData> findByBirthdateEquals(LocalDate birthdate, List<Long> allowedIds);

    @Query("SELECT d FROM PersonalData d WHERE d.birthdate BETWEEN ?1 AND ?2 AND d.user.id IN ?3")
    List<PersonalData> findByBirthdateBetween(LocalDate before,LocalDate after, List<Long> allowedIds);



    

}
