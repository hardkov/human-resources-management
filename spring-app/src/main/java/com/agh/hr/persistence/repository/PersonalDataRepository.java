package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    List<PersonalData> findByFirstname(String firstname);
    List<PersonalData> findByLastname(String lastname);
    List<PersonalData> findByFirstnameAndLastname(String firstname,String lastname);
    List<PersonalData> findByAddress(String address);
    List<PersonalData> findByPhoneNumber(String phoneNumber);
    List<PersonalData> findByEmail(String email);
    List<PersonalData> findByBirthdateEquals(LocalDate birthdate);
    List<PersonalData> findByBirthdateBetween(LocalDate before,LocalDate after);



    

}
