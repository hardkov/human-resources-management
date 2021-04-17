package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    Optional<List<PersonalData>> findByFirstname(String firstname);
    Optional<List<PersonalData>> findByLastname(String lastname);
    Optional<List<PersonalData>> findByAddress(String address);
    Optional<List<PersonalData>> findByPhoneNumber(String phoneNumber);
    Optional<List<PersonalData>> findByEmail(String email);
    Optional<List<PersonalData>> findByBirthdateEquals(LocalDate birthdate);
    Optional<List<PersonalData>> findByBirthdateBetween(LocalDate before,LocalDate after);



    

}
